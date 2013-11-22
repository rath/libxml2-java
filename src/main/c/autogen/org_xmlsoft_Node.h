/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class org_xmlsoft_Node */

#ifndef _Included_org_xmlsoft_Node
#define _Included_org_xmlsoft_Node
#ifdef __cplusplus
extern "C" {
#endif
#undef org_xmlsoft_Node_TYPE_ELEMENT
#define org_xmlsoft_Node_TYPE_ELEMENT 1L
#undef org_xmlsoft_Node_TYPE_ATTRIBUTE
#define org_xmlsoft_Node_TYPE_ATTRIBUTE 2L
#undef org_xmlsoft_Node_TYPE_TEXT
#define org_xmlsoft_Node_TYPE_TEXT 3L
#undef org_xmlsoft_Node_TYPE_CDATA
#define org_xmlsoft_Node_TYPE_CDATA 4L
#undef org_xmlsoft_Node_TYPE_ENTITY_REF
#define org_xmlsoft_Node_TYPE_ENTITY_REF 5L
#undef org_xmlsoft_Node_TYPE_ENTITY
#define org_xmlsoft_Node_TYPE_ENTITY 6L
#undef org_xmlsoft_Node_TYPE_PI
#define org_xmlsoft_Node_TYPE_PI 7L
#undef org_xmlsoft_Node_TYPE_COMMENT
#define org_xmlsoft_Node_TYPE_COMMENT 8L
#undef org_xmlsoft_Node_TYPE_DOCUMENT
#define org_xmlsoft_Node_TYPE_DOCUMENT 9L
#undef org_xmlsoft_Node_TYPE_DOCUMENT_TYPE
#define org_xmlsoft_Node_TYPE_DOCUMENT_TYPE 10L
#undef org_xmlsoft_Node_TYPE_DOCUMENT_FRAG
#define org_xmlsoft_Node_TYPE_DOCUMENT_FRAG 11L
#undef org_xmlsoft_Node_TYPE_NOTATION
#define org_xmlsoft_Node_TYPE_NOTATION 12L
#undef org_xmlsoft_Node_TYPE_DTD
#define org_xmlsoft_Node_TYPE_DTD 14L
/*
 * Class:     org_xmlsoft_Node
 * Method:    childrenImpl
 * Signature: ()Lorg/xmlsoft/Node;
 */
JNIEXPORT jobject JNICALL Java_org_xmlsoft_Node_childrenImpl
  (JNIEnv *, jobject);

/*
 * Class:     org_xmlsoft_Node
 * Method:    fillNamespaceImpl
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_org_xmlsoft_Node_fillNamespaceImpl
  (JNIEnv *, jobject);

/*
 * Class:     org_xmlsoft_Node
 * Method:    fillNameImpl
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_org_xmlsoft_Node_fillNameImpl
  (JNIEnv *, jobject);

/*
 * Class:     org_xmlsoft_Node
 * Method:    fillRequiredFields
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_org_xmlsoft_Node_fillRequiredFields
  (JNIEnv *, jobject);

/*
 * Class:     org_xmlsoft_Node
 * Method:    nextImpl
 * Signature: ()Lorg/xmlsoft/Node;
 */
JNIEXPORT jobject JNICALL Java_org_xmlsoft_Node_nextImpl
  (JNIEnv *, jobject);

/*
 * Class:     org_xmlsoft_Node
 * Method:    hasNext
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_org_xmlsoft_Node_hasNext
  (JNIEnv *, jobject);

/*
 * Class:     org_xmlsoft_Node
 * Method:    previousImpl
 * Signature: ()Lorg/xmlsoft/Node;
 */
JNIEXPORT jobject JNICALL Java_org_xmlsoft_Node_previousImpl
  (JNIEnv *, jobject);

/*
 * Class:     org_xmlsoft_Node
 * Method:    getParentImpl
 * Signature: ()Lorg/xmlsoft/Node;
 */
JNIEXPORT jobject JNICALL Java_org_xmlsoft_Node_getParentImpl
  (JNIEnv *, jobject);

/*
 * Class:     org_xmlsoft_Node
 * Method:    getLastImpl
 * Signature: ()Lorg/xmlsoft/Node;
 */
JNIEXPORT jobject JNICALL Java_org_xmlsoft_Node_getLastImpl
  (JNIEnv *, jobject);

/*
 * Class:     org_xmlsoft_Node
 * Method:    getTextImpl
 * Signature: (Z)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_org_xmlsoft_Node_getTextImpl
  (JNIEnv *, jobject, jboolean);

/*
 * Class:     org_xmlsoft_Node
 * Method:    getPropImpl
 * Signature: (Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_org_xmlsoft_Node_getPropImpl
  (JNIEnv *, jobject, jstring);

/*
 * Class:     org_xmlsoft_Node
 * Method:    fillAttributeNodes
 * Signature: (Ljava/util/List;)V
 */
JNIEXPORT void JNICALL Java_org_xmlsoft_Node_fillAttributeNodes
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_xmlsoft_Node
 * Method:    fillAttributeNames
 * Signature: (Ljava/util/List;)V
 */
JNIEXPORT void JNICALL Java_org_xmlsoft_Node_fillAttributeNames
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_xmlsoft_Node
 * Method:    getNsPropImpl
 * Signature: (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_org_xmlsoft_Node_getNsPropImpl
  (JNIEnv *, jobject, jstring, jstring);

/*
 * Class:     org_xmlsoft_Node
 * Method:    setPropImpl
 * Signature: (Ljava/lang/String;Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_org_xmlsoft_Node_setPropImpl
  (JNIEnv *, jobject, jstring, jstring);

/*
 * Class:     org_xmlsoft_Node
 * Method:    removePropImpl
 * Signature: (Ljava/lang/String;)Z
 */
JNIEXPORT jboolean JNICALL Java_org_xmlsoft_Node_removePropImpl
  (JNIEnv *, jobject, jstring);

/*
 * Class:     org_xmlsoft_Node
 * Method:    addChildImpl
 * Signature: (Lorg/xmlsoft/Node;)Lorg/xmlsoft/Node;
 */
JNIEXPORT jobject JNICALL Java_org_xmlsoft_Node_addChildImpl
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_xmlsoft_Node
 * Method:    unlinkImpl
 * Signature: (Lorg/xmlsoft/Node;)V
 */
JNIEXPORT void JNICALL Java_org_xmlsoft_Node_unlinkImpl
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_xmlsoft_Node
 * Method:    disposeImpl
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_org_xmlsoft_Node_disposeImpl
  (JNIEnv *, jobject);

/*
 * Class:     org_xmlsoft_Node
 * Method:    setTextImpl
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_org_xmlsoft_Node_setTextImpl
  (JNIEnv *, jobject, jstring);

/*
 * Class:     org_xmlsoft_Node
 * Method:    addPrevSiblingImpl
 * Signature: (Lorg/xmlsoft/Node;)Lorg/xmlsoft/Node;
 */
JNIEXPORT jobject JNICALL Java_org_xmlsoft_Node_addPrevSiblingImpl
  (JNIEnv *, jobject, jobject);

#ifdef __cplusplus
}
#endif
#endif
