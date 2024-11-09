/*
 * Copyright (c) 2024 by JWizard
 * Originally developed by Miłosz Gilga <https://miloszgilga.pl>
 */
package pl.jwizard.jwl.ioc.reflect

import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider
import org.springframework.core.type.filter.AnnotationTypeFilter
import pl.jwizard.jwl.AppRunner
import kotlin.reflect.KClass

/**
 * Scans the specified classpath subpackage to find classes annotated with a given annotation.
 *
 * This scanner is configured to locate candidate components based on the specified annotation and subpackage path.
 *
 * @param T The type of annotation to scan for.
 * @property annotationClazz The annotation class to look for.
 * @property subpackage The subpackage path within the base package to scan.
 * @author Miłosz Gilga
 */
class ClasspathScanner<T : Annotation>(
	private val annotationClazz: KClass<T>,
	private val subpackage: String,
) : ClassPathScanningCandidateComponentProvider(false) {

	init {
		addIncludeFilter(AnnotationTypeFilter(annotationClazz.java))
	}

	/**
	 * Scans the specified subpackage for classes annotated with the target annotation and returns a map of annotation
	 * instances to their corresponding classes.
	 *
	 * @return Map of annotation instances to the annotated class types.
	 */
	fun findComponents(): Map<T, Class<*>> {
		return findCandidateComponents("${AppRunner.BASE_PACKAGE}.$subpackage").associate {
			val clazz = Class.forName(it.beanClassName)
			clazz.getAnnotation(annotationClazz.java) to clazz
		}
	}
}
