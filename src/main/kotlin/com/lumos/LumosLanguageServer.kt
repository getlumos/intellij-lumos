package com.lumos

import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.openapi.project.Project
import com.redhat.devtools.lsp4ij.server.OSProcessStreamConnectionProvider

class LumosLanguageServer(project: Project) : OSProcessStreamConnectionProvider() {
    init {
        val commandLine = GeneralCommandLine(findLumosLspExecutable())
        setCommandLine(commandLine)
    }

    private fun findLumosLspExecutable(): String {
        // First, try to find lumos-lsp in standard Cargo bin directory
        val cargoHome = System.getenv("CARGO_HOME") ?: "${System.getProperty("user.home")}/.cargo"
        val cargoBinLumosLsp = "$cargoHome/bin/lumos-lsp"

        if (java.io.File(cargoBinLumosLsp).exists()) {
            return cargoBinLumosLsp
        }

        // Fallback to searching PATH
        return "lumos-lsp"
    }
}
