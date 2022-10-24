package io.jenkins.plugins.services.impl;

import java.util.List;

import javax.validation.constraints.NotNull;

import io.jenkins.plugins.models.Plugin;
import io.jenkins.plugins.services.ServiceException;
import org.apache.http.Header;

public interface WikiExtractor {

  /**
   * @param plugin plugin
   * @return API url that for accessing rendered content
   */
  String getApiUrl(@NotNull Plugin plugin);

  /**
   * <p>
   * Get clean wiki content so it's presentable to the UI
   * </p>
   *
   * @param apiContent content retrieved from API
   * @param plugin Plugin
   * @param service Client Wiki Service to use to get the content
   * @return cleaned content
   * @throws ServiceException in case something goes wrong
   */
  String extractHtml(@NotNull String apiContent, Plugin plugin, HttpClientWikiService service);

  /**
   * @return HTTP headers
   */
  List<Header> getHeaders();

}
