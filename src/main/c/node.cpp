#include "rath_libxml_Node.h"
#include <libxml/parser.h>
#include "utils.h"
#include <assert.h>

/*
 * Class:     rath_libxml_Node
 * Method:    childrenImpl
 * Signature: ()Lrath/libxml/Node;
 */
JNIEXPORT jobject JNICALL Java_rath_libxml_Node_childrenImpl
  (JNIEnv *env, jobject obj) {
    xmlNode *node = findNode(env, obj);
    xmlNode *children = node->children;
    return buildNode(env, children, obj);
}

/*
 * Class:     rath_libxml_Node
 * Method:    fillNamespaceImpl
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_rath_libxml_Node_fillNamespaceImpl
  (JNIEnv *env, jobject obj) {
    xmlNode *node = findNode(env, obj);
    xmlNs *ns = node->ns;
    if( ns==NULL )
        return;

    jstring jHref = env->NewStringUTF((const char*)ns->href);
    jstring jPrefix = env->NewStringUTF((const char*)ns->prefix);

    jclass cls = env->FindClass("rath/libxml/Namespace");
    jmethodID constructor = env->GetMethodID(cls, "<init>", "(Ljava/lang/String;Ljava/lang/String;)V");
    jobject jNs = env->NewObject(cls, constructor, jHref, jPrefix);

    jfieldID setterId = env->GetFieldID(env->GetObjectClass(obj), "namespace", "Lrath/libxml/Namespace;");
    env->SetObjectField(obj, setterId, jNs);
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
    xmlNode *node = findNode(env, obj);
    assert(node);
    jmethodID methodSetType = env->GetMethodID(env->GetObjectClass(obj), "setType", "(I)V");
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
    return buildNode(env, node->next, obj);
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
    return buildNode(env, node->prev, obj);
}

/*
 * Class:     rath_libxml_Node
 * Method:    getParentImpl
 * Signature: ()Lrath/libxml/Node;
 */
JNIEXPORT jobject JNICALL Java_rath_libxml_Node_getParentImpl
  (JNIEnv *env, jobject obj) {
    xmlNode *node = findNode(env, obj);
    return buildNode(env, node->parent, obj);
}

/*
 * Class:     rath_libxml_Node
 * Method:    getLastImpl
 * Signature: ()Lrath/libxml/Node;
 */
JNIEXPORT jobject JNICALL Java_rath_libxml_Node_getLastImpl
  (JNIEnv *env, jobject obj) {
    xmlNode *node = findNode(env, obj);
    return buildNode(env, node->last, obj);
}

/*
 * Class:     rath_libxml_Node
 * Method:    getTextImpl
 * Signature: (Z)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_rath_libxml_Node_getTextImpl
  (JNIEnv *env, jobject obj, jboolean format) {
    xmlNode *node = findNode(env, obj);
    const xmlChar *text = xmlNodeListGetString(node->doc, node, format==JNI_TRUE ? 1 : 0);
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

/*
 * Class:     rath_libxml_Node
 * Method:    fillAttributeNames
 * Signature: (Ljava/util/List;)V
 */
JNIEXPORT void JNICALL Java_rath_libxml_Node_fillAttributeNames
  (JNIEnv *env, jobject obj, jobject buffer) {
    xmlNode *node = findNode(env, obj);
    jmethodID methodAdd = env->GetMethodID(env->GetObjectClass(buffer), "add", "(Ljava/lang/Object;)Z");

    xmlAttr *attr = NULL;
    for(attr=node->properties; attr!=NULL; attr=attr->next) {
        jstring name = env->NewStringUTF((const char*)attr->name);
        env->CallBooleanMethod(buffer, methodAdd, name);
    }
}
