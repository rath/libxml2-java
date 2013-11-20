/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class org_xmlsoft_Node */

#ifndef _Included_org_xmlsoft_Node
#define _Included_org_xmlsoft_Node
#ifdef __cplusplus
extern "C" {
#endif
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