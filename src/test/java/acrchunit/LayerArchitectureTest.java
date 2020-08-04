package acrchunit;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures;

@AnalyzeClasses(packages = "br.com.orion.scheduledtransfer", importOptions = ImportOption.DoNotIncludeTests.class)
public class LayerArchitectureTest {

    private static final String PRESENTATION = "presentation";
    private static final String APPLICATION = "application";
    private static final String INFRASTRUCTURE = "infrastructure";
    private static final String DOMAIN = "domain";

    @ArchTest
    static final ArchRule layerArchitectureRules = Architectures.layeredArchitecture()
            .layer(PRESENTATION).definedBy("br.com.orion.scheduledtransfer.presentation..")
            .layer(APPLICATION).definedBy("br.com.orion.scheduledtransfer.application..")
            .layer(INFRASTRUCTURE).definedBy("br.com.orion.scheduledtransfer.infrastructure..")
            .layer(DOMAIN).definedBy("br.com.orion.scheduledtransfer.domain..")

            .whereLayer(PRESENTATION).mayNotBeAccessedByAnyLayer()
            .whereLayer(APPLICATION).mayOnlyBeAccessedByLayers(PRESENTATION)
            .whereLayer(INFRASTRUCTURE).mayOnlyBeAccessedByLayers(APPLICATION, PRESENTATION)
            .whereLayer(DOMAIN).mayOnlyBeAccessedByLayers(INFRASTRUCTURE, APPLICATION, PRESENTATION);

}
