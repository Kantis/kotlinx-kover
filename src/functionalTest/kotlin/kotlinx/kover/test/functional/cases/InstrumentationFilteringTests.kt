package kotlinx.kover.test.functional.cases

import kotlinx.kover.api.*
import kotlinx.kover.test.functional.cases.utils.*
import kotlinx.kover.test.functional.core.*
import kotlinx.kover.test.functional.core.BaseGradleScriptTest
import kotlin.test.*

internal class InstrumentationFilteringTests : BaseGradleScriptTest() {

    @Test
    fun testExclude() {
        builder("Test exclusion of classes from instrumentation")
            .languages(GradleScriptLanguage.KOTLIN, GradleScriptLanguage.GROOVY)
            .types(ProjectType.KOTLIN_JVM, ProjectType.KOTLIN_MULTIPLATFORM)
            .engines(CoverageEngine.INTELLIJ, CoverageEngine.JACOCO)
            .sources("simple")
            .configTest(
                """excludes = listOf("org.jetbrains.*Exa?ple*")""",
                """excludes = ['org.jetbrains.*Exa?ple*']"""
            )
            .build()
            .run("build") {
                xml(defaultXmlReport()) {
                    assertCounterExcluded(classCounter("org.jetbrains.ExampleClass"), this@run.engine)
                    assertCounterCovered(classCounter("org.jetbrains.SecondClass"))
                }
            }
    }

    @Test
    fun testExcludeInclude() {
        builder("Test inclusion and exclusion of classes in instrumentation")
            .languages(GradleScriptLanguage.KOTLIN, GradleScriptLanguage.GROOVY)
            .types(ProjectType.KOTLIN_JVM, ProjectType.KOTLIN_MULTIPLATFORM)
            .engines(CoverageEngine.INTELLIJ, CoverageEngine.JACOCO)
            .sources("simple")
            .configTest(
                """includes = listOf("org.jetbrains.*Cla?s")""",
                """includes = ['org.jetbrains.*Cla?s']"""
            )
            .configTest(
                """excludes = listOf("org.jetbrains.*Exa?ple*")""",
                """excludes = ['org.jetbrains.*Exa?ple*']"""
            )
            .build()
            .run("build") {
                xml(defaultXmlReport()) {
                    assertCounterExcluded(classCounter("org.jetbrains.ExampleClass"), this@run.engine)
                    assertCounterExcluded(classCounter("org.jetbrains.Unused"), this@run.engine)
                    assertCounterCovered(classCounter("org.jetbrains.SecondClass"))
                }
            }
    }

}
