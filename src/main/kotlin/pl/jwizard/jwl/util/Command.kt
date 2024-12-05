/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.util

/**
 * Converts a raw command (representing by [String]) by replacing all spaces with dots (`.`).
 *
 * @return A new string where all spaces in the original string are replaced with dots.
 * @author Miłosz Gilga
 */
fun String.rawCommandToDotFormat() = this.replace(" ", ".")
