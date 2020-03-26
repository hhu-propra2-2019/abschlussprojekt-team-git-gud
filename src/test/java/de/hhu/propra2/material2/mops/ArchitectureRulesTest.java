package de.hhu.propra2.material2.mops;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@AnalyzeClasses(packagesOf = Material2Application.class)
class ArchitectureRulesTest {

    @ArchTest
    private static ArchRule layerDependenciesAreRespected =
            layeredArchitecture()
                    .layer("database")
                    .definedBy("..database..")
                    .layer("businessLogic")
                    .definedBy("..domain..")
                    .layer("gui")
                    .definedBy("..controller..")
                    .whereLayer("gui")
                    .mayNotBeAccessedByAnyLayer()
                    .whereLayer("businessLogic")
                    .mayOnlyBeAccessedByLayers("gui")
                    .whereLayer("database")
                    .mayOnlyBeAccessedByLayers("businessLogic");
}
