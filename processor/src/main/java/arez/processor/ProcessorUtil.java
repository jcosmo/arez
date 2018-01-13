package arez.processor;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;
import java.lang.annotation.Documented;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.AnnotatedConstruct;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

final class ProcessorUtil
{
  static final String SENTINEL_NAME = "<default>";

  private ProcessorUtil()
  {
  }

  @Nonnull
  static List<TypeVariableName> getTypeArgumentsAsNames( @Nonnull final DeclaredType declaredType )
  {
    final List<TypeVariableName> variables = new ArrayList<>();
    for ( final TypeMirror argument : declaredType.getTypeArguments() )
    {
      variables.add( TypeVariableName.get( (TypeVariable) argument ) );
    }
    return variables;
  }

  @Nonnull
  static List<VariableElement> getFieldElements( @Nonnull final TypeElement element )
  {
    final Map<String, VariableElement> methodMap = new LinkedHashMap<>();
    enumerateFieldElements( element, methodMap );
    return new ArrayList<>( methodMap.values() );
  }

  private static void enumerateFieldElements( @Nonnull final TypeElement element,
                                              @Nonnull final Map<String, VariableElement> fields )
  {
    final TypeMirror superclass = element.getSuperclass();
    if ( TypeKind.NONE != superclass.getKind() )
    {
      enumerateFieldElements( (TypeElement) ( (DeclaredType) superclass ).asElement(), fields );
    }
    for ( final Element member : element.getEnclosedElements() )
    {
      if ( member.getKind() == ElementKind.FIELD )
      {
        fields.put( member.getSimpleName().toString(), (VariableElement) member );
      }
    }
  }

  @Nonnull
  static List<ExecutableElement> getMethods( @Nonnull final TypeElement element,
                                             @Nonnull final Types typeUtils )
  {
    final Map<String, ExecutableElement> methodMap = new LinkedHashMap<>();
    enumerateMethods( element, typeUtils, element, methodMap );
    return new ArrayList<>( methodMap.values() );
  }

  private static void enumerateMethods( @Nonnull final TypeElement scope,
                                        @Nonnull final Types typeUtils,
                                        @Nonnull final TypeElement element,
                                        @Nonnull final Map<String, ExecutableElement> methods )
  {
    final TypeMirror superclass = element.getSuperclass();
    if ( TypeKind.NONE != superclass.getKind() )
    {
      enumerateMethods( scope, typeUtils, (TypeElement) ( (DeclaredType) superclass ).asElement(), methods );
    }
    for ( final TypeMirror interfaceType : element.getInterfaces() )
    {
      final TypeElement interfaceElement = (TypeElement) ( (DeclaredType) interfaceType ).asElement();
      enumerateMethods( scope, typeUtils, interfaceElement, methods );
    }
    for ( final Element member : element.getEnclosedElements() )
    {
      if ( member.getKind() == ElementKind.METHOD )
      {
        final ExecutableType methodType =
          (ExecutableType) typeUtils.asMemberOf( (DeclaredType) scope.asType(), member );
        methods.put( member.getSimpleName() + methodType.toString(), (ExecutableElement) member );
      }
    }
  }

  @Nonnull
  static List<ExecutableElement> getConstructors( @Nonnull final TypeElement element )
  {
    return element.getEnclosedElements().stream().
      filter( m -> m.getKind() == ElementKind.CONSTRUCTOR ).
      map( m -> (ExecutableElement) m ).
      collect( Collectors.toList() );
  }

  static void copyAccessModifiers( @Nonnull final TypeElement element, @Nonnull final TypeSpec.Builder builder )
  {
    if ( element.getModifiers().contains( Modifier.PUBLIC ) )
    {
      builder.addModifiers( Modifier.PUBLIC );
    }
  }

  static void copyAccessModifiers( @Nonnull final ExecutableElement element, @Nonnull final MethodSpec.Builder builder )
  {
    if ( element.getModifiers().contains( Modifier.PUBLIC ) )
    {
      builder.addModifiers( Modifier.PUBLIC );
    }
    else if ( element.getModifiers().contains( Modifier.PROTECTED ) )
    {
      builder.addModifiers( Modifier.PROTECTED );
    }
  }

  static void copyDocumentedAnnotations( @Nonnull final AnnotatedConstruct element,
                                         @Nonnull final MethodSpec.Builder builder )
  {
    for ( final AnnotationMirror annotation : element.getAnnotationMirrors() )
    {
      final DeclaredType annotationType = annotation.getAnnotationType();
      if ( !annotationType.toString().startsWith( "arez.annotations." ) &&
           null != annotationType.asElement().getAnnotation( Documented.class ) )
      {
        builder.addAnnotation( AnnotationSpec.get( annotation ) );
      }
    }
  }

  static void copyDocumentedAnnotations( @Nonnull final AnnotatedConstruct element,
                                         @Nonnull final ParameterSpec.Builder builder )
  {
    for ( final AnnotationMirror annotation : element.getAnnotationMirrors() )
    {
      if ( null != annotation.getAnnotationType().asElement().getAnnotation( Documented.class ) )
      {
        builder.addAnnotation( AnnotationSpec.get( annotation ) );
      }
    }
  }

  static void copyExceptions( @Nonnull final ExecutableType method, @Nonnull final MethodSpec.Builder builder )
  {
    for ( final TypeMirror thrownType : method.getThrownTypes() )
    {
      builder.addException( TypeName.get( thrownType ) );
    }
  }

  static void copyTypeParameters( @Nonnull final ExecutableType action, @Nonnull final MethodSpec.Builder builder )
  {
    for ( final TypeVariable typeParameter : action.getTypeVariables() )
    {
      builder.addTypeVariable( TypeVariableName.get( typeParameter ) );
    }
  }

  @Nullable
  static String deriveName( @Nonnull final ExecutableElement method,
                            @Nonnull final Pattern pattern,
                            @Nonnull final String name )
    throws ArezProcessorException
  {
    if ( isSentinelName( name ) )
    {
      final String methodName = method.getSimpleName().toString();
      final Matcher matcher = pattern.matcher( methodName );
      if ( matcher.find() )
      {
        final String candidate = matcher.group( 1 );
        return Character.toLowerCase( candidate.charAt( 0 ) ) + candidate.substring( 1 );
      }
      else
      {
        return null;
      }
    }
    else
    {
      return name;
    }
  }

  static boolean isSentinelName( @Nonnull final String name )
  {
    return SENTINEL_NAME.equals( name );
  }

  static boolean isJavaIdentifier( @Nonnull final String value )
  {
    if ( value.isEmpty() )
    {
      return false;
    }
    else if ( !Character.isJavaIdentifierStart( value.charAt( 0 ) ) )
    {
      return false;
    }
    else
    {
      final int length = value.length();
      for ( int i = 1; i < length; i++ )
      {
        if ( !Character.isJavaIdentifierPart( value.charAt( i ) ) )
        {
          return false;
        }
      }

      return true;
    }
  }

  @SuppressWarnings( { "unchecked", "SameParameterValue" } )
  @Nonnull
  static List<TypeMirror> getTypeMirrorsAnnotationParameter( @Nonnull final Elements elements,
                                                             @Nonnull final Element typeElement,
                                                             @Nonnull final String annotationClassName,
                                                             @Nonnull final String parameterName )
  {
    final AnnotationValue annotationValue =
      getAnnotationValue( elements, typeElement, annotationClassName, parameterName );
    return ( (List<AnnotationValue>) annotationValue.getValue() ).stream().
      map( v -> (TypeMirror) v.getValue() ).collect( Collectors.toList() );
  }

  @Nonnull
  static AnnotationValue getAnnotationValue( @Nonnull final Elements elements,
                                             @Nonnull final Element typeElement,
                                             @Nonnull final String annotationClassName,
                                             @Nonnull final String parameterName )
  {
    final AnnotationValue value = findAnnotationValue( elements, typeElement, annotationClassName, parameterName );
    assert null != value;
    return value;
  }

  @Nullable
  private static AnnotationValue findAnnotationValue( @Nonnull final Elements elements,
                                                      @Nonnull final Element typeElement,
                                                      @Nonnull final String annotationClassName,
                                                      @Nonnull final String parameterName )
  {
    final AnnotationMirror mirror = getAnnotationByType( typeElement, annotationClassName );
    return findAnnotationValue( elements, mirror, parameterName );
  }

  @Nullable
  private static AnnotationValue findAnnotationValue( @Nonnull final Elements elements,
                                                      @Nonnull final AnnotationMirror annotation,
                                                      @Nonnull final String parameterName )
  {
    final Map<? extends ExecutableElement, ? extends AnnotationValue> values =
      elements.getElementValuesWithDefaults( annotation );
    final ExecutableElement annotationKey = values.keySet().stream().
      filter( k -> parameterName.equals( k.getSimpleName().toString() ) ).findFirst().orElse( null );
    return values.get( annotationKey );
  }

  @Nonnull
  static AnnotationValue getAnnotationValue( @Nonnull final Elements elements,
                                             @Nonnull final AnnotationMirror annotation,
                                             @Nonnull final String parameterName )
  {
    final AnnotationValue value = findAnnotationValue( elements, annotation, parameterName );
    assert null != value;
    return value;
  }

  @Nonnull
  static AnnotationMirror getAnnotationByType( @Nonnull final Element typeElement,
                                               @Nonnull final String annotationClassName )
  {
    AnnotationMirror mirror = findAnnotationByType( typeElement, annotationClassName );
    assert null != mirror;
    return mirror;
  }

  @Nullable
  static AnnotationMirror findAnnotationByType( @Nonnull final Element typeElement,
                                                @Nonnull final String annotationClassName )
  {
    return typeElement.getAnnotationMirrors().stream().
      filter( a -> a.getAnnotationType().toString().equals( annotationClassName ) ).findFirst().orElse( null );
  }

  @Nonnull
  static String toSimpleName( @Nonnull final String annotationName )
  {
    return annotationName.replaceAll( ".*\\.", "" );
  }
}