package main

import (
	"errors"
	"fmt"
	"io"
	"log"
	"net/http"
	"os"
	"text/template"
)

var (
	PORT       = ":8080"
	UPLOAD_DIR = "upload"
	// Compile templates on start of the application
	templates = template.Must(template.ParseFiles("public/upload.html"))
)

// Display the named template
func display(w http.ResponseWriter, page string, data interface{}) {
	templates.ExecuteTemplate(w, page+".html", data)
}

func uploadFile(w http.ResponseWriter, r *http.Request) {
	// Maximum upload of 10 MB files
	r.ParseMultipartForm(10 << 20)

	// Get handler for filename, size and headers
	file, handler, err := r.FormFile("myFile")
	if err != nil {
		fmt.Println("Error Retrieving the File")
		fmt.Println(err)
		return
	}

	defer file.Close()
	fmt.Printf("Uploaded File: %+v\n", handler.Filename)
	fmt.Printf("File Size: %+v\n", handler.Size)
	fmt.Printf("MIME Header: %+v\n", handler.Header)

	// Create file
	filename := fmt.Sprintf("%s/%s", UPLOAD_DIR, handler.Filename)
	dst, err := os.Create(filename)
	defer dst.Close()
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}

	// Copy the uploaded file to the created file on the filesystem
	if _, err := io.Copy(dst, file); err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}

	fmt.Fprintf(w, "Successfully Uploaded File\n")
}

func uploadHandler(w http.ResponseWriter, r *http.Request) {
	switch r.Method {
	case "GET":
		display(w, "upload", nil)
	case "POST":
		uploadFile(w, r)
	}
}

func main() {
	// Create upload folder if not exist
	if _, err := os.Stat(UPLOAD_DIR); errors.Is(err, os.ErrNotExist) {
		if err := os.Mkdir(UPLOAD_DIR, os.ModePerm); err != nil {
			panic(err)
		}
	}

	// Upload route
	http.HandleFunc("/upload", uploadHandler)

	fmt.Println("Listening on port", PORT)
	//Listen on port 8080
	log.Fatal(http.ListenAndServe(PORT, nil))

}
