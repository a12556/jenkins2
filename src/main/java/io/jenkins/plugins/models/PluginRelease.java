package io.jenkins.plugins.models;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vdurmont.emoji.EmojiParser;

import org.commonmark.ext.autolink.AutolinkExtension;
import org.commonmark.ext.gfm.strikethrough.StrikethroughExtension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.ins.InsExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PluginRelease {
  private Logger logger = LoggerFactory.getLogger(PluginRelease.class);

  @JsonProperty("tag_name") final private String tagName;
  @JsonProperty("name") final private String name;
  @JsonProperty("published_at") final private Date publishedAt;
  @JsonProperty("html_url") final private String htmlUrl;
  private String body;

  @JsonProperty("bodyHTML") public String getBodyHTML() {
    String baseUrl = "";

    try {
      baseUrl = new URI(this.htmlUrl + "/../../../issues").normalize().toString();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }

    List<org.commonmark.Extension> extensions = Arrays.asList(
        TablesExtension.create(),
        AutolinkExtension.create(),
        StrikethroughExtension.create(),
        InsExtension.create()
    );
    HtmlRenderer htmlRenderer = HtmlRenderer.builder().extensions(extensions).escapeHtml(true).sanitizeUrls(true).build();
    Parser markdownParser = Parser.builder().extensions(extensions).build();

    Node document = markdownParser.parse(
        EmojiParser.parseToUnicode(
          this.body
            .replaceAll("<!--.*?-->", "")
            .replaceAll("#\\b([0-9]+)\\b", "[#$1](" + baseUrl + "/$1)")
            .replaceAll("(\\b|\\s)+@([a-zA-Z\\d](?:[a-zA-Z\\d]|-(?=[a-zA-Z\\d])){0,38})(\\b|\\s)+", "$1[**@$2**](https://github.com/$2)$3")
        )
    );
    return htmlRenderer
      .render(document)
      .replaceAll("(\\\t|\\\n)", "")
      .replaceAll("\\s+", " ")
      .trim();
  }


  @JsonCreator
  public PluginRelease(@JsonProperty("tag_name") String tagName, @JsonProperty("name") String name, @JsonProperty("published_at") Date publishedAt, @JsonProperty("html_url") String htmlUrl, @JsonProperty("body") String body) {
    this.tagName = tagName;
    this.name = name;
    this.publishedAt = publishedAt;
    this.htmlUrl = htmlUrl;
    this.body = body;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof PluginRelease)) {
      return false;
    }
    return this.tagName.equals(((PluginRelease) obj).tagName) &&
      this.name.equals(((PluginRelease) obj).name) &&
      this.publishedAt.equals(((PluginRelease) obj).publishedAt) &&
      this.body.equals(((PluginRelease) obj).body);
  }
}
