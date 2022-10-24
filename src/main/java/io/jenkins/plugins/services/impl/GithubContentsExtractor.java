package io.jenkins.plugins.services.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.jenkins.plugins.models.Plugin;

public class GithubContentsExtractor extends GithubExtractor {
  private final static class GithubContentMatcher implements GithubMatcher {
    private final Matcher matcher;

    private GithubContentMatcher(Matcher matcher) {
      this.matcher = matcher;
    }

    @Override
    public String getEndpoint() {
      return String.format(CONTENTS_ENDPOINT, matcher.group(4));
    }

    @Override
    public String getDirectory() {
      String filePath = matcher.group(4);
      return "/" + filePath.substring(0, filePath.lastIndexOf("/") + 1);
    }

    @Override
    public String getBranch() {
      return matcher.group(3);
    }

    @Override
    public boolean find() {
      return matcher.find();
    }

    @Override
    public String getRepo() {
      return matcher.group(2);
    }

    @Override
    public String getOrganization() {
      return matcher.group(1);
    }
  }

  private static final Pattern REPO_PATTERN = Pattern
      .compile("https?://github.com/(jenkinsci|jenkins-infra)/([^/.]+)/blob/([^/]+)/(.+\\.(md|adoc))$");

  private static final String CONTENTS_ENDPOINT = "contents/%s";

  @Override
  protected GithubMatcher getDelegate(Plugin plugin) {
    final Matcher matcher = REPO_PATTERN.matcher(plugin.getWikiUrl());
    return new GithubContentMatcher(matcher);
  }

}
