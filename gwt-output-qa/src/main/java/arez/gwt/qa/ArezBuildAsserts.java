package arez.gwt.qa;

import javax.annotation.Nonnull;
import org.intellij.lang.annotations.RegExp;
import org.realityforge.gwt.symbolmap.SymbolEntryIndex;

/**
 * A collection of assertions about the expected symbols present in GWT javascript output.
 */
public final class ArezBuildAsserts
{
  private ArezBuildAsserts()
  {
  }

  /**
   * This assertion verifies that the standard inlines have occurred.
   *
   * @param index the index that contains all symbols for output target.
   */
  public static void assertStandardOutputs( @Nonnull final SymbolEntryIndex index )
  {
    // This should never appear as it is not meant to be GWT compiled
    index.assertNoClassNameMatches( "arez\\.ArezTestUtil" );
    index.assertNoMemberMatches( "arez\\.ArezZoneHolder", "getDefaultZone" );
    index.assertNoMemberMatches( "arez\\.ArezZoneHolder", "getZoneStack" );

    // This should be optimized out completely
    index.assertNoClassNameMatches( "arez\\.ArezConfig" );

    // This should be eliminated as it will improve the ability for GWT compiler to dead-code-eliminate
    index.assertNoMemberMatches( "arez\\.Arez", "$clinit" );
  }

  /**
   * This assertion verifies that the symbols that are conditional on the `arez.enforce_transaction_type`
   * setting are present if enabled and not present if not enabled.
   *
   * @param index   the index that contains all symbols for output target.
   * @param enabled true if setting is enabled, false otherwise.
   */
  public static void assertShouldEnforceTransactionTypeOutputs( @Nonnull final SymbolEntryIndex index,
                                                                final boolean enabled )
  {
    // Assert no Transaction validation cruft is enabled as !Arez.shouldEnforceTransactionType() in the build
    index.assertSymbol( "arez\\.TransactionMode", enabled );
  }

  /**
   * This assertion verifies that the symbols that are conditional on the `arez.repositories_results_modifiable`
   * setting are present if enabled and not present if not enabled.
   *
   * @param index   the index that contains all symbols for output target.
   * @param enabled true if setting is enabled, false otherwise.
   */
  public static void assertAreRepositoryResultsModifiableOutputs( @Nonnull final SymbolEntryIndex index,
                                                                  final boolean enabled )
  {
    // Assert RepositoryUtil is eliminated once !Arez.areRepositoryResultsModifiable() in the build
    index.assertSymbol( "arez\\.component\\.RepositoryUtil", enabled );
  }

  /**
   * This assertion verifies that the symbols that are conditional on the `arez.enable_observer_error_handlers`
   * setting are present if enabled and not present if not enabled.
   *
   * @param index   the index that contains all symbols for output target.
   * @param enabled true if setting is enabled, false otherwise.
   */
  public static void assertAreObserverErrorHandlersEnabledOutputs( @Nonnull final SymbolEntryIndex index,
                                                                   final boolean enabled )
  {
    index.assertSymbol( "arez\\.ObserverErrorHandler", enabled );
    index.assertSymbol( "arez\\.ObserverErrorHandlerSupport", enabled );
    // This is actually only elided when both Spy and ObserverErrorHandler are both disabled but mostly
    // if observer error handlers are disabled then spies are disabled so adding this assertion here
    index.assertSymbol( "arez\\.ObserverError", enabled );
  }

  /**
   * This assertion verifies that the symbols that are conditional on the `arez.enable_names`
   * setting are present if enabled and not present if not enabled.
   *
   * @param index   the index that contains all symbols for output target.
   * @param enabled true if setting is enabled, false otherwise.
   */
  public static void assertAreNamesEnabled( @Nonnull final SymbolEntryIndex index,
                                            final boolean enabled )
  {
    index.assertSymbol( "arez\\.ThrowableUtil", enabled );
    index.assertSymbol( ".*\\.Arez_.*Repository", "getRepositoryName", enabled );
    index.assertSymbol( ".*\\.Arez_.*", "toString", enabled );
  }

  /**
   * This assertion verifies that the symbols that are conditional on the `arez.enable_registries`
   * setting are present if enabled and not present if not enabled.
   *
   * @param index   the index that contains all symbols for output target.
   * @param enabled true if setting is enabled, false otherwise.
   */
  public static void assertAreRegistriesEnabled( @Nonnull final SymbolEntryIndex index,
                                                 final boolean enabled )
  {
    index.assertSymbol( "arez\\.ArezContext", "_observables", enabled );
    index.assertSymbol( "arez\\.ArezContext", "_computedValues", enabled );
    index.assertSymbol( "arez\\.ArezContext", "_observers", enabled );
  }

  /**
   * This assertion verifies that the symbols that are conditional on the `arez.enable_spies`
   * setting are present if enabled and not present if not enabled.
   *
   * @param index   the index that contains all symbols for output target.
   * @param enabled true if setting is enabled, false otherwise.
   */
  public static void assertSpyOutputs( @Nonnull final SymbolEntryIndex index, final boolean enabled )
  {
    index.assertSymbol( "arez\\.spy\\..*", enabled );
    index.assertSymbol( "arez\\.Spy.*", enabled );
    index.assertSymbol( "arez\\..*InfoImpl", enabled );
  }

  /**
   * This assertion verifies that the symbols that are conditional on the `arez.enable_zones`
   * setting are present if enabled and not present if not enabled.
   *
   * @param index   the index that contains all symbols for output target.
   * @param enabled true if setting is enabled, false otherwise.
   */
  public static void assertZoneOutputs( @Nonnull final SymbolEntryIndex index, final boolean enabled )
  {

    index.assertSymbol( "arez\\.Zone", enabled );
    index.assertSymbol( "arez\\.ArezZoneHolder", enabled );
    index.assertSymbol( "arez\\.Arez", "createZone", enabled );
    index.assertSymbol( "arez\\.Arez", "activateZone", enabled );
    index.assertSymbol( "arez\\.Arez", "deactivateZone", enabled );
    index.assertSymbol( "arez\\.Arez", "currentZone", enabled );
    index.assertSymbol( ".*\\.Arez_.*", "$$arezi$$_context", enabled );
    index.assertSymbol( "arez\\.Node", "_context", enabled );
  }

  /**
   * This assertion verifies that the symbols that are conditional on the `arez.enable_native_components`
   * setting are present if enabled and not present if not enabled.
   *
   * @param index   the index that contains all symbols for output target.
   * @param enabled true if setting is enabled, false otherwise.
   */
  public static void assertNativeComponentOutputs( @Nonnull final SymbolEntryIndex index, final boolean enabled )
  {
    // Assert no Component cruft is enabled as !Arez.areNativeComponentsEnabled() in the build
    index.assertSymbol( "arez\\.Component.*", enabled );
    index.assertSymbol( ".*\\.Arez_.*", "$$arezi$$_component", enabled );
    index.assertSymbol( ".*\\.Arez_.*Repository", "component", enabled );

    // No repositories need their own identity if native components disabled
    assertSyntheticId( index, ".*\\.Arez_[^\\.]+Repository", false );
  }

  /**
   * Assert that a synthetic id is present or not present in classes specified by pattern.
   *
   * @param index            the index that contains all symbols for output target.
   * @param classNamePattern the pattern that determine which classes should be matched.
   * @param enabled          true if syntheticId should be present, false otherwise.
   */
  public static void assertSyntheticId( @Nonnull final SymbolEntryIndex index,
                                        @RegExp( prefix = "^", suffix = "$" ) @Nonnull final String classNamePattern,
                                        final boolean enabled )
  {
    index.assertSymbol( classNamePattern, "$$arezi$$_id", enabled );
    index.assertSymbol( classNamePattern, "$$arezi$$_nextId", enabled );
  }

  /**
   * Assert normal arez outputs based on specified Arez compile time settings.
   *
   * @param index                           the index that contains all symbols for output target.
   * @param areNamesEnabled                 the value of the `arez.enable_names` setting.
   * @param areSpiesEnabled                 the value of the `arez.enable_spies` setting.
   * @param areNativeComponentsEnabled      the value of the `arez.enable_native_components` setting.
   * @param areRegistriesEnabled            the value of the `arez.enable_registries` setting.
   * @param areObserverErrorHandlersEnabled the value of the `arez.enable_observer_error_handlers` setting.
   * @param areZonesEnabled                 the value of the `arez.enable_zones` setting.
   * @param shouldEnforceTransactionType    the value of the `arez.enforce_transaction_type` setting.
   * @param areRepositoryResultsModifiable  the value of the `arez.repositories_results_modifiable` setting.
   */
  public static void assertArezOutputs( @Nonnull final SymbolEntryIndex index,
                                        final boolean areNamesEnabled,
                                        final boolean areSpiesEnabled,
                                        final boolean areNativeComponentsEnabled,
                                        final boolean areRegistriesEnabled,
                                        final boolean areObserverErrorHandlersEnabled,
                                        final boolean areZonesEnabled,
                                        final boolean shouldEnforceTransactionType,
                                        final boolean areRepositoryResultsModifiable )
  {
    assertStandardOutputs( index );
    assertAreNamesEnabled( index, areNamesEnabled );
    assertSpyOutputs( index, areSpiesEnabled );
    assertNativeComponentOutputs( index, areNativeComponentsEnabled );
    assertAreRegistriesEnabled( index, areRegistriesEnabled );
    assertAreObserverErrorHandlersEnabledOutputs( index, areObserverErrorHandlersEnabled );
    assertZoneOutputs( index, areZonesEnabled );
    assertShouldEnforceTransactionTypeOutputs( index, shouldEnforceTransactionType );
    assertAreRepositoryResultsModifiableOutputs( index, areRepositoryResultsModifiable );
  }
}