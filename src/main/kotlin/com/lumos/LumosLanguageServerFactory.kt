package com.lumos

import com.intellij.openapi.project.Project
import com.redhat.devtools.lsp4ij.LanguageServerFactory
import com.redhat.devtools.lsp4ij.client.LanguageClientImpl
import com.redhat.devtools.lsp4ij.server.StreamConnectionProvider
import org.jetbrains.annotations.NotNull

class LumosLanguageServerFactory : LanguageServerFactory {
    override fun createConnectionProvider(@NotNull project: Project): StreamConnectionProvider {
        return LumosLanguageServer(project)
    }

    override fun createLanguageClient(@NotNull project: Project): LanguageClientImpl {
        return LumosLanguageClient(project)
    }
}
