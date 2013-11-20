/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class rath_libxml_Document */

#ifndef _Included_rath_libxml_Document
#define _Included_rath_libxml_Document
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     rath_libxml_Document
 * Method:    getRootElementImpl
 * Signature: ()Lrath/libxml/Node;
 */
JNIEXPORT jobject JNICALL Java_rath_libxml_Document_getRootElementImpl
  (JNIEnv *, jobject);

/*
 * Class:     rath_libxml_Document
 * Method:    setVersionImpl
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_rath_libxml_Document_setVersionImpl
  (JNIEnv *, jobject, jstring);

/*
 * Class:     rath_libxml_Document
 * Method:    getVersionImpl
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_rath_libxml_Document_getVersionImpl
  (JNIEnv *, jobject);

/*
 * Class:     rath_libxml_Document
 * Method:    createXPathContextImpl
 * Signature: ()Lrath/libxml/XPathContext;
 */
JNIEXPORT jobject JNICALL Java_rath_libxml_Document_createXPathContextImpl
  (JNIEnv *, jobject);

/*
 * Class:     rath_libxml_Document
 * Method:    disposeImpl
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_rath_libxml_Document_disposeImpl
  (JNIEnv *, jobject);

/*
 * Class:     rath_libxml_Document
 * Method:    createElementImpl
 * Signature: (Lrath/libxml/Namespace;Ljava/lang/String;)Lrath/libxml/Node;
 */
JNIEXPORT jobject JNICALL Java_rath_libxml_Document_createElementImpl
  (JNIEnv *, jobject, jobject, jstring);

/*
 * Class:     rath_libxml_Document
 * Method:    createTextImpl
 * Signature: (Ljava/lang/String;)Lrath/libxml/Node;
 */
JNIEXPORT jobject JNICALL Java_rath_libxml_Document_createTextImpl
  (JNIEnv *, jobject, jstring);

/*
 * Class:     rath_libxml_Document
 * Method:    createCommentImpl
 * Signature: (Ljava/lang/String;)Lrath/libxml/Node;
 */
JNIEXPORT jobject JNICALL Java_rath_libxml_Document_createCommentImpl
  (JNIEnv *, jobject, jstring);

/*
 * Class:     rath_libxml_Document
 * Method:    createCDataImpl
 * Signature: (Ljava/lang/String;)Lrath/libxml/Node;
 */
JNIEXPORT jobject JNICALL Java_rath_libxml_Document_createCDataImpl
  (JNIEnv *, jobject, jstring);

/*
 * Class:     rath_libxml_Document
 * Method:    createPIImpl
 * Signature: (Ljava/lang/String;Ljava/lang/String;)Lrath/libxml/Node;
 */
JNIEXPORT jobject JNICALL Java_rath_libxml_Document_createPIImpl
  (JNIEnv *, jobject, jstring, jstring);

/*
 * Class:     rath_libxml_Document
 * Method:    createDocumentImpl
 * Signature: (Ljava/lang/String;)J
 */
JNIEXPORT jlong JNICALL Java_rath_libxml_Document_createDocumentImpl
  (JNIEnv *, jclass, jstring);

/*
 * Class:     rath_libxml_Document
 * Method:    saveImpl
 * Signature: (Ljava/lang/String;Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_rath_libxml_Document_saveImpl
  (JNIEnv *, jobject, jstring, jstring);

/*
 * Class:     rath_libxml_Document
 * Method:    saveStreamImpl
 * Signature: (Ljava/io/OutputStream;Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_rath_libxml_Document_saveStreamImpl
  (JNIEnv *, jobject, jobject, jstring);

/*
 * Class:     rath_libxml_Document
 * Method:    saveWriterImpl
 * Signature: (Ljava/io/Writer;Ljava/lang/String;Ljava/nio/charset/Charset;)V
 */
JNIEXPORT void JNICALL Java_rath_libxml_Document_saveWriterImpl
  (JNIEnv *, jobject, jobject, jstring, jobject);

#ifdef __cplusplus
}
#endif
#endif
