/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.file

import org.apache.commons.vfs2.FileObject
import org.apache.commons.vfs2.VFS
import org.springframework.core.io.ClassPathResource
import pl.jwizard.jwl.util.logger
import java.io.IOException
import java.util.*

/**
 * A class for browsing and listing files from a directory in the application's classpath.
 *
 * This class provides methods to interact with a directory (given by the [path] parameter), including listing all
 * files and identifying specific subdirectories like those related to internationalization (i18n).
 *
 * @property path The path to the directory that will be browsed. This is typically a classpath resource.
 * @author Miłosz Gilga
 */
class IndependentFileBrowser(private val path: String) {

	companion object {
		private val log = logger<IndependentFileBrowser>()
	}

	/**
	 * The resource representing the directory specified by [path].
	 */
	private val resource = ClassPathResource(path)

	/**
	 * Lists all i18n project directories under the given path. The directories are expected to follow a structure where
	 * each directory has subdirectories containing "messages" files for internationalization.
	 *
	 * @return A list of strings representing paths to i18n project directories. If the directories cannot be found, an
	 *         empty list is returned.
	 */
	fun getI18nProjectDirectories() = try {
		val directoryUri = VFS.getManager().resolveFile(resource.uri)
		directoryUri.children.map { "${it.parent.name.baseName}/${it.name.baseName}/messages" }
	} catch (ex: IOException) {
		log.error("Unable to list i18n directories from: {}. Cause: {}.", path, ex.message)
		emptyList()
	}

	/**
	 * Lists all files in the directory specified by the [path] property. The method traverses the directory and its
	 * subdirectories recursively, collecting the names of all files it finds.
	 *
	 * @return A list of strings representing the paths to all files within the directory and its subdirectories. If
	 *         there is an error during traversal, an empty list is returned.
	 */
	fun listAllFilesFromDirectory() = try {
		val directoryUri = VFS.getManager().resolveFile(resource.uri)

		val elements = mutableListOf<String>()
		val stack = Stack<FileObject>()

		if (directoryUri.isFolder) {
			val basePath = directoryUri.name.path
			stack.push(directoryUri)
			while (stack.isNotEmpty()) {
				val currentDir = stack.pop()
				for (child in currentDir.children) {
					if (child.isFolder) {
						stack.push(child)
					} else {
						elements += child.name.path.removePrefix(basePath).substring(1)
					}
				}
			}
		}
		elements
	} catch (ex: IOException) {
		log.error("Unable to list all files from directory: {}. Cause: {}.", path, ex.message)
		emptyList()
	}
}
