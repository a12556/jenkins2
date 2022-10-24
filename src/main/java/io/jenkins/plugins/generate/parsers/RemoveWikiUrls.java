package io.jenkins.plugins.generate.parsers;

import io.jenkins.plugins.generate.PluginDataParser;
import io.jenkins.plugins.models.Plugin;
import io.jenkins.plugins.models.Wiki;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.common.Strings;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;

public class RemoveWikiUrls implements PluginDataParser {

  private static final Logger logger = LoggerFactory.getLogger(RemoveWikiUrls.class);

  private static final String URL = "https://updates.jenkins.io/current/plugin-documentation-urls.json";

  private static final Pattern WIKI_URL_REGEXP_TITLE = Pattern
    .compile("^https?://wiki.jenkins(-ci.org|.io)/display/(jenkins|hudson)/([^/]*)/?$", Pattern.CASE_INSENSITIVE);

  private static final Pattern WIKI_HOST_REGEXP = Pattern.compile("^https?://wiki.jenkins(-ci.org|.io)", Pattern.CASE_INSENSITIVE);

  public RemoveWikiUrls() {
  }

  private void setUrl(Plugin plugin) {
    if (plugin.getWiki() == null) {
      plugin.setWiki(new Wiki());
    }
    plugin.getWiki().setUrl(String.format("https://github.com/jenkins-infra/plugins-wiki-docs/blob/master/%s/README.md", plugin.getName()));
  }

  @Override
  public void parse(JSONObject pluginJson, Plugin plugin) {
    if (Strings.isNullOrEmpty(plugin.getWikiUrl())) {
      return;
    }
    if (WIKI_URL_REGEXP_TITLE.matcher(plugin.getWikiUrl()).find()) {
      setUrl(plugin);
    } else if (WIKI_HOST_REGEXP.matcher(plugin.getWikiUrl()).find()) {
      setUrl(plugin);
    }
  }
}
