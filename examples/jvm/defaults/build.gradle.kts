plugins {
    kotlin("jvm") version "1.7.10"
    id("org.jetbrains.kotlinx.kover") version "0.6.1"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

kover {
    isDisabled = false

    useKoverToolDefault()

    excludeInstrumentation {
        className("com.example.subpackage.*")
    }
}

koverReport {
    filters {
        excludes {
            className("com.example.subpackage.*")
        }
        includes {
            className("com.example.*")
        }
    }

    xml {
        onCheck = false
        setReportFile(layout.buildDirectory.file("my-project-report/result.xml"))

        filters {
            excludes {
                className("com.example2.subpackage.*")
            }
            includes {
                className("com.example2.*")
            }
        }
    }

    html {
        onCheck = false
        setReportDir(layout.buildDirectory.dir("my-project-report/html-result"))

        filters {
            excludes {
                className("com.example2.subpackage.*")
            }
            includes {
                className("com.example2.*")
            }
        }
    }

    verify {
        onCheck = true
        rule {
            isEnabled = true
            name = null
            entity = kotlinx.kover.gradle.plugin.dsl.GroupingEntityType.APPLICATION

            filters {
                excludes {
                    className("com.example.verify.subpackage.*")
                }
                includes {
                    className("com.example.verify.*")
                }
            }

            bound {
                minValue = 1
                maxValue = 99
                metric = kotlinx.kover.gradle.plugin.dsl.MetricType.LINE
                aggregation = kotlinx.kover.gradle.plugin.dsl.AggregationType.COVERED_PERCENTAGE
            }
        }
    }
}