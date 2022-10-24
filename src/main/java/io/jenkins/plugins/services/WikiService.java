package io.jenkins.plugins.services;

import io.jenkins.plugins.models.Plugin;

/**
 * <p>Responsible for retrieving and cleaning wiki content for a plugin</p>
 */
public interface WikiService {

  /**
   * <p>Get wiki content</p>
   *
   * @param plugin Plugin to fetch docs from
   * @return content
   * @throws ServiceException in case something goes wrong
   */
  String getWikiContent(Plugin plugin) throws ServiceException;
}
