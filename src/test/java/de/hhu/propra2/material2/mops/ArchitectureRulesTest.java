package de.hhu.propra2.material2.mops;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@AnalyzeClasses(packagesOf = Material2Application.class)
public class ArchitectureRulesTest {

    @ArchTest
    static ArchRule layer_dependencies_are_respected = layeredArchitecture()
            .layer("persistence").definedBy("..persistence..")
            .layer("domain").definedBy("..domain..")
            .layer("gui").definedBy("..Controller..")
            .whereLayer("gui").mayNotBeAccessedByAnyLayer()
            .whereLayer("domain").mayOnlyBeAccessedByLayers("gui")
            .whereLayer("persistence").mayOnlyBeAccessedByLayers("domain");
}
