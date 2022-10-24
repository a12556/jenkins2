package io.jenkins.plugins.models;

import org.junit.Test;

import java.util.Date;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

public class PluginReleaseTest {

  @Test
  public void disallowsScaryHtml() {
    PluginRelease pluginRelease = new PluginRelease(
      "tagName",
      "name",
      new Date(),
      "https://github.com/jenkinsci/lighthouse-report-plugin/releases/tag/lighthouse-report-0.2",
      "# header1\n## header2\n <script>alert('hi')</script>"
    );
    assertThat(
      pluginRelease.getBodyHTML(),
      containsString("&lt;script&gt;alert('hi')&lt;/script&gt;")
    );
  }

  @Test
  public void rightLink() {
    PluginRelease pluginRelease = new PluginRelease(
      "saml-1.1.5",
      "1.1.5",
      new Date(),
      "https://github.com/jenkinsci/saml-plugin/releases/tag/saml-1.1.5",
      "<!-- Optional: add a release summary here -->\r\n## \uD83D\uDC1B Bug Fixes\r\n\r\n* Handle windows paths (#78) @willwh\r\n\r\n## \uD83D\uDCE6 Dependency updates\r\n\r\n* [JENKINS-60742](https://issues.jenkins-ci.org/browse/JENKINS-60742) - Bump core to 2.176.1 version and bump plugin dependecies (#82) @kuisathaverat\r\n* [JENKINS-60679](https://issues.jenkins-ci.org/browse/JENKINS-60679) - Bump bouncycastle api plugin due NoSuchMethodError exception (#81) @kuisathaverat\r\n\r\n## \uD83D\uDCDD Documentation updates\r\n\r\n* Update TROUBLESHOOTING.md (#80) @duemir\r\n* fix typo (#79) @tehmaspc\r\n"
    );
    assertThat(
      pluginRelease.getBodyHTML(),
      containsString("<h2>🐛 Bug Fixes</h2><ul><li>Handle windows paths (<a rel=\"nofollow\" href=\"https://github.com/jenkinsci/saml-plugin/issues/78\">#78</a>) <a rel=\"nofollow\" href=\"https://github.com/willwh\"><strong>@willwh</strong></a></li></ul><h2>📦 Dependency updates</h2><ul><li><a rel=\"nofollow\" href=\"https://issues.jenkins-ci.org/browse/JENKINS-60742\">JENKINS-60742</a> - Bump core to 2.176.1 version and bump plugin dependecies (<a rel=\"nofollow\" href=\"https://github.com/jenkinsci/saml-plugin/issues/82\">#82</a>) <a rel=\"nofollow\" href=\"https://github.com/kuisathaverat\"><strong>@kuisathaverat</strong></a></li><li><a rel=\"nofollow\" href=\"https://issues.jenkins-ci.org/browse/JENKINS-60679\">JENKINS-60679</a> - Bump bouncycastle api plugin due NoSuchMethodError exception (<a rel=\"nofollow\" href=\"https://github.com/jenkinsci/saml-plugin/issues/81\">#81</a>) <a rel=\"nofollow\" href=\"https://github.com/kuisathaverat\"><strong>@kuisathaverat</strong></a></li></ul><h2>📝 Documentation updates</h2><ul><li>Update TROUBLESHOOTING.md (<a rel=\"nofollow\" href=\"https://github.com/jenkinsci/saml-plugin/issues/80\">#80</a>) <a rel=\"nofollow\" href=\"https://github.com/duemir\"><strong>@duemir</strong></a></li><li>fix typo (<a rel=\"nofollow\" href=\"https://github.com/jenkinsci/saml-plugin/issues/79\">#79</a>) <a rel=\"nofollow\" href=\"https://github.com/tehmaspc\"><strong>@tehmaspc</strong></a></li></ul>")
    );
  }

  @Test
  public void rightLink2() {
    PluginRelease pluginRelease = new PluginRelease(
      "javadoc-1.6",
      "1.6",
      new Date(),
      "https://github.com/jenkinsci/javadoc-plugin/releases/tag/javadoc-1.6",
      "## \uD83D\uDE80 New features and improvements\r\n\r\n* Add `javadoc` symbol to the Javadoc step to simplify usage in Jenkins Pipeline (#12) @ybroeker\r\n\r\n## \uD83D\uDCDD Documentation updates\r\n\r\n* Move changelog to GitHub Releases (#14) @oleg-nenashev\r\n* Move docs from Wiki to GitHub (#13) @MarkEWaite\r\n\r\n## \uD83D\uDC7B Maintenance\r\n\r\n* Build the plugin with both JDK 1.8 and JDK 11 (#11) @batmat \r\n* Add Release Drafter (#14) @oleg-nenashev\r\n\r\n"
    );
    assertThat(
      pluginRelease.getBodyHTML(),
      containsString("<h2>🚀 New features and improvements</h2><ul><li>Add <code>javadoc</code> symbol to the Javadoc step to simplify usage in Jenkins Pipeline (<a rel=\"nofollow\" href=\"https://github.com/jenkinsci/javadoc-plugin/issues/12\">#12</a>) <a rel=\"nofollow\" href=\"https://github.com/ybroeker\"><strong>@ybroeker</strong></a></li></ul><h2>📝 Documentation updates</h2><ul><li>Move changelog to GitHub Releases (<a rel=\"nofollow\" href=\"https://github.com/jenkinsci/javadoc-plugin/issues/14\">#14</a>) <a rel=\"nofollow\" href=\"https://github.com/oleg-nenashev\"><strong>@oleg-nenashev</strong></a></li><li>Move docs from Wiki to GitHub (<a rel=\"nofollow\" href=\"https://github.com/jenkinsci/javadoc-plugin/issues/13\">#13</a>) <a rel=\"nofollow\" href=\"https://github.com/MarkEWaite\"><strong>@MarkEWaite</strong></a></li></ul><h2>👻 Maintenance</h2><ul><li>Build the plugin with both JDK 1.8 and JDK 11 (<a rel=\"nofollow\" href=\"https://github.com/jenkinsci/javadoc-plugin/issues/11\">#11</a>) <a rel=\"nofollow\" href=\"https://github.com/batmat\"><strong>@batmat</strong></a></li><li>Add Release Drafter (<a rel=\"nofollow\" href=\"https://github.com/jenkinsci/javadoc-plugin/issues/14\">#14</a>) <a rel=\"nofollow\" href=\"https://github.com/oleg-nenashev\"><strong>@oleg-nenashev</strong></a></li></ul>")
    );
  }
}
