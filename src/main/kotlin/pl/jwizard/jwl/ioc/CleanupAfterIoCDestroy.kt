package pl.jwizard.jwl.ioc

import org.springframework.beans.factory.DisposableBean

// use this interface on beans, which have some closeable content
interface CleanupAfterIoCDestroy : DisposableBean
