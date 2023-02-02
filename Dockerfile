FROM golang:latest

#RUN mkdir app

WORKDIR /app

COPY . .
# Build the application
RUN go env -w GO111MODULE=off
RUN go build -o main .
# Expose port 8080 to the outside world
EXPOSE 8080

# Command to run the executable
CMD ["./main"]
