package de.hhu.propra2.material2.mops;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@AnalyzeClasses(packagesOf = Material2Application.class)
public class ArchitectureRulesTest {

    @ArchTest
    static ArchRule layer_dependencies_are_respected = layeredArchitecture()
            .layer("Database").definedBy("..Database..")
            .layer("businessLogic").definedBy("..domain..")
            .layer("gui").definedBy("..controller..")
            .whereLayer("gui").mayNotBeAccessedByAnyLayer()
            .whereLayer("businessLogic").mayOnlyBeAccessedByLayers("gui")
            .whereLayer("Database").mayOnlyBeAccessedByLayers("businessLogic");
}
