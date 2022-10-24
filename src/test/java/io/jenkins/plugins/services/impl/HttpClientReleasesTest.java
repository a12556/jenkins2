package io.jenkins.plugins.services.impl;

import io.jenkins.plugins.models.Plugin;
import io.jenkins.plugins.models.PluginRelease;
import io.jenkins.plugins.models.PluginReleases;
import io.jenkins.plugins.models.Scm;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.message.BasicHeader;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class HttpClientReleasesTest {
  @Rule
  public PluginSiteApiWireMockRule wireMockRule = new PluginSiteApiWireMockRule(HttpClientReleasesTest.class, Arrays.asList(
    "https://api.github.com"
  ));

  private HttpClientReleases httpClientReleases;

  static final private Plugin lighthouseReportPlugin = new Plugin() {
    @Override
    public Scm getScm() {
      return new Scm() {
        @Override
        public String getLink() {
          return "https://github.com/jenkinsci/lighthouse-report-plugin";
        }
      };
    }
  };
  static final private Plugin badScmPlugin = new Plugin() {
    @Override
    public Scm getScm() {
      return new Scm() {
        @Override
        public String getLink() {
          return "https://randomwebsite.com/for/some/reason";
        }
      };
    }
  };

  @Before
  public void setUp() {
    this.httpClientReleases = new HttpClientReleases(new DefaultConfigurationService() {
      @Override
      public List<Header> getGithubCredentials() {
        return Collections.singletonList(new BasicHeader(HttpHeaders.AUTHORIZATION, "Bearer faketoken"));
      }

      @Override
      public String getGithubApiBase() {
        return wireMockRule.baseUrl();
      }
    });
  }

  @Test
  public void getReleasesHappy() throws Exception {
    PluginReleases releases = this.httpClientReleases.getReleases(lighthouseReportPlugin);
    assertEquals(1, releases.getReleases().size());
    assertEquals(
      new PluginRelease(
        "lighthouse-report-0.1.0",
        "Lighthouse Report Plugin - 0.1.0",
        Date.from(Instant.ofEpochMilli(1590280223000L)),
        "https://github.com/jenkinsci/lighthouse-report-plugin/releases/tag/lighthouse-report-0.2",
        "<!-- Optional: add a release summary here -->\r\n\r\n## 🚀 New features and improvements\r\n\r\n* Enable multiple reports (#2) @nishant-gupta\r\n\r\n## 📦 Dependency updates\r\n\r\n* Bump react-lighthouse-viewer from 2.0.0 to 2.8.0 (#7) @dependabot\r\n* [Security] Bump acorn from 5.7.3 to 5.7.4 (#6) @dependabot\r\n* Bump plugin from 3.50 to 4.2 (#3) @dependabot\r\n* Bump babel-eslint from 10.0.3 to 10.1.0 (#5) @dependabot\r\n* Bump react from 16.10.2 to 16.13.1 (#9) @dependabot\r\n* Bump eslint from 6.5.1 to 6.8.0 (#10) @dependabot\r\n"
      ),
      releases.getReleases().get(0)
    );
  }

  @Test
  public void getReleasesBadScmLink() throws Exception {
    PluginReleases releases = this.httpClientReleases.getReleases(badScmPlugin);
    assertEquals(0, releases.getReleases().size());
  }
}
