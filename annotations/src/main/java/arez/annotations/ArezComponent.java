package arez.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import javax.annotation.Nonnull;

/**
 * Annotation that marks classes to be processed by Arez annotation processor.
 * Classes with this annotation can contain {@link Observable} properties,
 * {@link Computed} properties, {@link Autorun} methods, {@link Track} methods
 * and {@link Action} methods.
 *
 * <p>The annotation controls the way that contained actions and observables are
 * named (if names are enabled in the system.</p>
 * <ul>
 * <li>The value returned by {@link #name()} indicates the type name for instances
 * of this object. If not specified it will default to the SimpleName of the class.
 * i.e. The class <tt>com.biz.models.MyModel</tt> will default to a name of
 * "MyModel".</li>
 * <li>The {@link #nameIncludesId()} method indicates whether the names in generated components
 * should include the id. If you expect only one instance of this component, it can be simpler
 * to elide the id.</li>
 * </ul>
 * <p>The name of any elements contained within the component follows the pattern
 * "<tt>[ArezComponent.name].[ArezComponent.id].[Element.name]</tt>". If the value of {@link #nameIncludesId()}
 * is false then the "<tt>[ArezComponent.id].</tt>" element of the name will be elided.</p>
 *
 * <p>The type that is annotated with <tt>@ArezComponent</tt> annotation must comply with the additional constraints:</p>
 * <ul>
 * <li>Must be a class, not an interface or enum</li>
 * <li>Must be concrete, not abstract</li>
 * <li>Must not be final</li>
 * <li>Must not be a non-static nested class</li>
 * <li>Must have at least one method annotated with {@link Action}, {@link Autorun}, {@link Track}, {@link Computed} or {@link Observable}</li>
 * </ul>
 *
 * <p>The annotation processor that handles this annotation will analyze all super classes and super
 * interfaces. This includes analysis of default methods on interfaces. So it is perfectly valid to
 * add {@link Observable}, {@link Computed}, {@link Action}, {@link PreDispose} and {@link PostDispose}
 * annotations to default methods on implemented interfaces.</p>
 */
@Documented
@Target( ElementType.TYPE )
public @interface ArezComponent
{
  /**
   * Return the name of the type.
   * The value must conform to the requirements of a java identifier.
   *
   * @return the name of the type.
   */
  @Nonnull
  String name() default "<default>";

  /**
   * Return true if the name derived for component should include the id, false otherwise.
   * If the user does not specify a value for this parameter but does specify the <tt>javax.inject.Singleton</tt>
   * on the same type then the default value of this parameter is false, otherwise it is true.
   *
   * @return true to include the id in the component name, false otherwise.
   */
  boolean nameIncludesId() default true;

  /**
   * Return true if the component does not need to explicitly declare elements.
   * Otherwise if no elements (i.e. {@link Observable}s, {@link Action}s, {@link Autorun}s etc) are defined
   * on a component it will generate an error.
   *
   * @return true if the component does not need to explicitly declare elements, false otherwise.
   */
  boolean allowEmpty() default false;

  /**
   * Return true if the component type does not need to be abstract.
   * Otherwise an error will be generated if this annotation is added to a concrete type.
   *
   * @return true if the component type does not need to be abstract.
   */
  boolean allowConcrete() default false;

  /**
   * Return true if the component should dispose itself once it is no longer "observed".
   * By "observed" it means that the component will have {@link arez.component.ComponentObservable#observe(Object)}
   * called on it.
   *
   * @return true if the component should dispose itself once it is no longer "observed".
   */
  boolean disposeOnDeactivate() default false;

  /**
   * Indicate whether an @Inject annotation should be added to constructor of the generated class.
   * {@link Feature#ENABLE} will force the addition of an @Inject annotation, {@link Feature#DISABLE}
   * will result in no @Inject annotation and {@link Feature#AUTODETECT} will add an @Inject
   * if any fields or methods in the component or any parent type has an @Inject annotation OR the class
   * has an annotation that is itself annotated with the javax.inject.Scope annotation.
   *
   * @return enum controlling present of Inject annotation on constructor.
   */
  Feature inject() default Feature.AUTODETECT;

  /**
   * Indicate whether a dagger module should be generated for enhanced component class.
   * {@link Feature#ENABLE} will force the generation of the module, {@link Feature#DISABLE}
   * will result in no dagger module and {@link Feature#AUTODETECT} will add a dagger
   * module if the <code>dagger.Module</code> class is present on the classpath AND the class
   * has an annotation that is itself annotated with the javax.inject.Scope annotation.
   *
   * @return enum controlling whether a dagger module should be generated for repository.
   */
  Feature dagger() default Feature.AUTODETECT;

  /**
   * Return true if an the generated component should NOT schedule autorun actions at the end of the
   * constructor. This is useful if the component creator will trigger schedules manually at a later time.
   *
   * @return Return true if an the generated component should NOT schedule autorun actions at the end of the constructor.
   */
  boolean deferSchedule() default false;

  /**
   * Indicate whether a component requires that the {@link Object#hashCode()} and {@link Object#equals(Object)}
   * methods are implemented. These methods MUST be implemented if the {@link Repository} annotation is present
   * but may be implemented in other scenarios. {@link Feature#ENABLE} will force the generation of the methods,
   * {@link Feature#DISABLE} will not generate these methods and {@link Feature#AUTODETECT} will generated these
   * methods if the {@link Repository} annotation is present.
   *
   * @return enum controlling whether the {@link Object#hashCode()} and {@link Object#equals(Object)} methods are implemented.
   */
  Feature requireEquals() default Feature.AUTODETECT;
}
