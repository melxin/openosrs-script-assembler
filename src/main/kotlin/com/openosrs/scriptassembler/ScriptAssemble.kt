/*
 * Copyright (c) 2022, Owain van Brakel <https://github.com/Owain94>
 * All rights reserved.
 *
 * This code is licensed under GPL3, see the complete license in
 * the LICENSE file in the root directory of this source tree.
 */
package com.openosrs.scriptassembler

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.*
import org.gradle.api.file.RegularFileProperty

@CacheableTask
abstract class ScriptAssemble : DefaultTask() {
    @get:InputDirectory
    @get:PathSensitive(PathSensitivity.NONE)
    abstract val input: DirectoryProperty

    @get:OutputDirectory
    abstract val output: DirectoryProperty

    @get:InputFile
    @get:PathSensitive(PathSensitivity.NONE)
    abstract val componentsFile: RegularFileProperty

    init {
        componentsFile.convention(project.layout.projectDirectory.file("../runelite-api/src/main/interfaces/interfaces.toml"))
    }

    @TaskAction
    fun assembleScripts() {
        val scriptAssembler: ScriptAssemblerTaskHandler = ScriptAssembler(input.get().asFileTree, output.get(), componentsFile.get().asFile)

        scriptAssembler.assemble()
        scriptAssembler.buildIndex()
    }
}