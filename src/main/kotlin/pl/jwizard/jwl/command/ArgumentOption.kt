/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.command

import pl.jwizard.jwl.TextKeyExtractor
import pl.jwizard.jwl.i18n.I18nLocaleSource

/**
 * Represents a customizable argument option that extends both locale-specific and text-key extraction capabilities.
 * This interface is intended for command argument options requiring internationalization support and key extraction.
 *
 * Classes implementing this interface should provide methods to extract localized keys and handle text localization
 * seamlessly.
 *
 * @author Miłosz Gilga
 * @see I18nLocaleSource
 * @see TextKeyExtractor
 */
interface ArgumentOption : I18nLocaleSource, TextKeyExtractor
