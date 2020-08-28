package de.idnow.customerportal.three;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("de.idnow.customerportal.three");

        noClasses()
            .that()
                .resideInAnyPackage("de.idnow.customerportal.three.service..")
            .or()
                .resideInAnyPackage("de.idnow.customerportal.three.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..de.idnow.customerportal.three.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
