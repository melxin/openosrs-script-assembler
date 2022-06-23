/*
 * Copyright (c) 2022, Owain van Brakel <https://github.com/Owain94>
 * All rights reserved.
 *
 * This code is licensed under GPL3, see the complete license in
 * the LICENSE file in the root directory of this source tree.
 */
package com.openosrs.scriptassembler

import org.gradle.api.Plugin
import org.gradle.api.Project

class ScriptAssemblerPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            tasks.create("assembleScripts", ScriptAssemble::class.java)
        }
    }
}