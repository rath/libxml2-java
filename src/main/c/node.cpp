#include "rath_libxml_Node.h"
#include <libxml/parser.h>
#include "utils.h"

/*
 * Class:     rath_libxml_Node
 * Method:    childrenImpl
 * Signature: ()Lrath/libxml/Node;
 */
JNIEXPORT jobject JNICALL Java_rath_libxml_Node_childrenImpl
  (JNIEnv *env, jobject obj) {
    xmlNode *node = findNode(env, obj);
    xmlNode *children = node->children;
    return buildNode(env, children);
}

/*
 * Class:     rath_libxml_Node
 * Method:    fillNameImpl
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_rath_libxml_Node_fillNameImpl
  (JNIEnv *env, jobject obj) {
    xmlNode *node = findNode(env, obj);
    jstring nodeName = env->NewStringUTF((const char*)node->name);

    jfieldID fieldSetName = env->GetFieldID(env->GetObjectClass(obj), "name", "Ljava/lang/String;");
    env->SetObjectField(obj, fieldSetName, nodeName);
}

/*
 * Class:     rath_libxml_Node
 * Method:    fillRequiredFields
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_rath_libxml_Node_fillRequiredFields
  (JNIEnv *env, jobject obj) {
    // type
    jmethodID methodSetType = env->GetMethodID(env->GetObjectClass(obj), "setType", "(I)V");
    xmlNode *node = findNode(env, obj);
    env->CallVoidMethod(obj, methodSetType, node->type);
}

/*
 * Class:     rath_libxml_Node
 * Method:    nextImpl
 * Signature: ()Lrath/libxml/Node;
 */
JNIEXPORT jobject JNICALL Java_rath_libxml_Node_nextImpl
  (JNIEnv *env, jobject obj) {
    xmlNode *node = findNode(env, obj);
    if (node->next==NULL )
        return NULL;
    return buildNode(env, node->next);
}

/*
 * Class:     rath_libxml_Node
 * Method:    hasNext
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_rath_libxml_Node_hasNext
  (JNIEnv *env, jobject obj) {
    return findNode(env, obj)->next!=NULL;
}

/*
 * Class:     rath_libxml_Node
 * Method:    previousImpl
 * Signature: ()Lrath/libxml/Node;
 */
JNIEXPORT jobject JNICALL Java_rath_libxml_Node_previousImpl
  (JNIEnv *env, jobject obj) {
    xmlNode *node = findNode(env, obj);
    if (node->prev==NULL )
        return NULL;
    return buildNode(env, node->prev);
}

/*
 * Class:     rath_libxml_Node
 * Method:    getChildTextImpl
 * Signature: (Z)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_rath_libxml_Node_getChildTextImpl
  (JNIEnv *env, jobject obj, jboolean format) {
    xmlNode *node = findNode(env, obj);
    const xmlChar *text = xmlNodeListGetString(node->doc, node->children, format==JNI_TRUE ? 1 : 0);
    jstring jText = env->NewStringUTF((const char*)text);
    xmlFree((void*)text);
    return jText;
}

/*
 * Class:     rath_libxml_Node
 * Method:    getPropImpl
 * Signature: (Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_rath_libxml_Node_getPropImpl
  (JNIEnv *env, jobject obj, jstring jprop) {
    xmlNode *node = findNode(env, obj);

    xmlChar *prop = (xmlChar*)env->GetStringUTFChars(jprop, NULL);
    xmlChar *value = xmlGetProp(node, prop);
    if (value==NULL)
        return NULL;

    jstring jvalue = env->NewStringUTF((const char*)value);
    xmlFree(value);
    return jvalue;
}
