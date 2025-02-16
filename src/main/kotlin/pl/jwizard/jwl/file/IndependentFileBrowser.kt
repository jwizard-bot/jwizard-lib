package pl.jwizard.jwl.file

import org.apache.commons.vfs2.FileObject
import org.apache.commons.vfs2.VFS
import org.springframework.core.io.ClassPathResource
import pl.jwizard.jwl.util.logger
import java.io.IOException
import java.util.*

// files reader from .jar library (for example read resources from resources directory in embed
// .jar library
class IndependentFileBrowser(private val path: String) {
	companion object {
		private val log = logger<IndependentFileBrowser>()
	}

	private val resource = ClassPathResource(path)

	fun getI18nProjectDirectories() = try {
		val directoryUri = VFS.getManager().resolveFile(resource.uri)
		directoryUri.children.map { "${it.parent.name.baseName}/${it.name.baseName}/messages" }
	} catch (ex: IOException) {
		log.error("Unable to list i18n directories from: \"{}\". Cause: {}.", path, ex.message)
		emptyList()
	}

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
		log.warn("Directory: \"{}\" not exist. Skipping load directories and files.", path)
		emptyList()
	}
}
