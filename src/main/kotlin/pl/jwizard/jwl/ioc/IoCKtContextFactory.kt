package pl.jwizard.jwl.ioc

import org.springframework.context.annotation.AnnotationConfigApplicationContext
import pl.jwizard.jwl.jvm.JvmDisposable
import pl.jwizard.jwl.jvm.JvmDisposableHook
import pl.jwizard.jwl.util.logger
import kotlin.reflect.KClass

class IoCKtContextFactory(
	clazz: KClass<*>,
) : AnnotationConfigApplicationContext(clazz.java), JvmDisposable {
	companion object {
		private val log = logger<IoCKtContextFactory>()
	}

	private val jvmDisposableHook = JvmDisposableHook(this)

	init {
		jvmDisposableHook.initHook()
	}

	fun <T : Any> getBean(beanType: KClass<T>): T = super.getBean(beanType.java)

	inline fun <reified T : Any, reified U : Annotation> getBeansAnnotatedWith(): List<T> =
		getBeansWithAnnotation(U::class.java).map { it.value as T }

	inline fun <reified T : Any> getBeansWithSupertype() =
		getBeansOfType(T::class.java).map { it.value as T }

	override fun cleanBeforeDisposeJvm() {
		log.info("Shutting down and close internal Spring Context factory...")
		close()
	}
}
