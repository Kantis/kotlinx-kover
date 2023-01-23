/*
 * Copyright 2017-2023 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license.
 */

package kotlinx.kover.gradle.plugin.dsl

import kotlinx.kover.gradle.plugin.commons.KoverMigrations
import kotlinx.kover.gradle.plugin.dsl.KoverVersions.JACOCO_TOOL_DEFAULT_VERSION
import kotlinx.kover.gradle.plugin.dsl.KoverVersions.KOVER_TOOL_DEFAULT_VERSION
import org.gradle.api.*

public interface KoverProjectExtension {

    /**
     * Disable instrumentation of all tests of this project, also all kover tasks of the current projects are not
     * executed, even if whey called directly.
     */
    public var isDisabled: Boolean

    /**
     * Coverage Tool by Kover.
     */
    public fun useKoverToolDefault()

    /**
     * JaCoCo Coverage Tool with default version [JACOCO_TOOL_DEFAULT_VERSION].
     */
    public fun useJacocoToolDefault()

    /**
     * Kover Coverage Tool with default version [KOVER_TOOL_DEFAULT_VERSION].
     */
    public fun useKoverTool(version: String)

    /**
     * Coverage Tool by [JaCoCo](https://www.jacoco.org/jacoco/).
     */
    public fun useJacocoTool(version: String)

    public fun excludeTests(config: Action<KoverTestsExclusions>)

    public fun excludeSources(config: Action<KoverSourcesExclusions>)

    public fun excludeInstrumentation(config: Action<KoverInstrumentationExclusions>)


    /**
     * Property is deprecated, please use `use...Tool...()` functions.
     */
    @Deprecated(
        message = "Property was removed. Please refer to migration guide in order to migrate: ${KoverMigrations.MIGRATION_0_6_TO_0_7}",
        level = DeprecationLevel.ERROR
    )
    public val engine: Nothing?
        get() = null
}


public interface KoverTestsExclusions: KoverTaskDefinitions {
    public override fun taskName(vararg name: String)

    public override fun taskName(names: Iterable<String>)

    public fun kmpTargetName(vararg name: String)
}


public interface KoverSourcesExclusions: KoverKmpCompilationDefinitions {
    public var excludeJavaCode: Boolean

    public fun jvmSourceSetName(vararg name: String)

    public fun jvmSourceSetName(names: Iterable<String>)

    public override fun kmpTargetName(vararg name: String)

    public override fun kmpCompilation(targetName: String, compilationName: String)

    public override fun kmpCompilation(compilationName: String)
}

public interface KoverInstrumentationExclusions: KoverClassDefinitions {
    public override fun className(vararg className: String)

    public override fun className(classNames: Iterable<String>)

    public override fun packageName(vararg className: String)

    public override fun packageName(classNames: Iterable<String>)
}
