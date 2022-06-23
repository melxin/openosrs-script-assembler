/*
 * Copyright (c) 2019, Lucas <https://github.com/Lucwousin>
 * All rights reserved.
 *
 * This code is licensed under GPL3, see the complete license in
 * the LICENSE file in the root directory of this source tree.
 */
package com.openosrs.scriptassembler;

/**
 * Interface containing all the methods gradle needs to know about
 */
public interface ScriptAssemblerTaskHandler
{
	/**
	 * The actual method that does all the work
	 */
	void assemble();

	/**
	 * Call this to save the vanilla hash
	 */
	void buildIndex();
}
