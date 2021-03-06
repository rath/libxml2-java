AC_PREREQ([2.68])
AC_INIT([libxml2-java], [0.1], [http://github.com/rath/libxml2-java])
AC_CONFIG_SRCDIR([src/main/c/libxml.c])
AC_CONFIG_HEADERS([config.h])

# Checks for programs.
AC_PROG_CC
AC_PROG_CXX

# Checks for header files.
AC_CHECK_HEADERS([stdlib.h string.h])
# Checks for typedefs, structures, and compiler characteristics.
AC_TYPE_SIZE_T

# Check libxml2 
AC_CHECK_PROG([have_xml2config], [xml2-config], [yes], [no])

if test x"${have_xml2config}" = x"no"; then
  AC_MSG_ERROR([
  +--------------------------------------------+
  | The 'xml2-config' script required to build |
  | libxml2-java module. You need to install   |
  | $ sudo apt-get install libxml2-dev         |
  |   or                                       |
  | $ sudo yum install libxml2-devel           |
  | Check 'config.log' for more information.   |
  ---------------------------------------------+])
fi
AC_SUBST([LIBS_XML2], [`xml2-config --libs`])
AC_SUBST([CFLAGS_XML2], [`xml2-config --cflags`])

#
# Check Google TCMalloc 
#
AC_ARG_WITH([tcmalloc], 
  [AS_HELP_STRING([--with-tcmalloc],
    [link with google tcmalloc])],
  [with_tcmalloc=yes], [with_tcmalloc=no])
if test x"${with_tcmalloc}" = x"yes"; then 
  AC_CHECK_HEADERS([google/tcmalloc.h])
  AC_CHECK_LIB([tcmalloc], [tc_malloc], [have_tcmalloc=yes], [
    AC_MSG_ERROR([
    +--------------------------------------------+
    | Google TCMalloc not found                  |
    +--------------------------------------------+])])
  AC_SUBST([LIBS_TCMALLOC], [-ltcmalloc])
fi


#
# With JDK home directory 
#
AC_ARG_WITH([jdk], 
  [AS_HELP_STRING([--with-jdk=DIR], 
    [use jdk home directory (default: autodetect)])], 
  [jdk_dir=${withval}], [jdk_dir=])

#
# Determine JNI header location 
#
platform=$(uname|tr '[A-Z]' '[a-z]')

if test x"${jdk_dir}" = x; then
  if test x"${platform}" = x"darwin"; then
    jdk_dir=$(/usr/libexec/java_home) 
  fi
  if test x"${platform}" = x"linux"; then
    loc_of_javac=$(readlink -f /etc/alternatives/javac)
    loc_of_javabin=$(dirname "${loc_of_javac}")
    jdk_dir=$(dirname "${loc_of_javabin}")
  fi
fi

AC_CHECK_FILE([${jdk_dir}/include/jni.h], [have_jni_h=yes], [have_jni_h=no])
AC_CHECK_FILE([${jdk_dir}/include/${platform}], [have_jni_m=yes], [have_jni_m=no])

stop=no
if test x"${have_jni_h}" = x"no"; then 
  stop=yes
fi
if test x"${have_jni_m}" = x"no"; then 
  stop=yes
fi

if test x"${stop}" = x"yes"; then
  AC_MSG_ERROR([
  +------------------------------------------------+
  | Java Development Kit home directory not found. |
  | JNI header files are required to build.        |
  | Please install JDK instead of JRE. Stopping... |
  +------------------------------------------------+])
fi 
AC_SUBST([JDK_INC_DIRS], 
  ["-I${jdk_dir}/include -I${jdk_dir}/include/${platform}"])


# Determine wheter -shared or -dynamiclib
AC_SUBST([JNILIB], [libxml2j.so])

cflag_of_sharedlib=-shared
if test x"${platform}" = x"darwin"; then
  cflag_of_sharedlib=-dynamiclib
  AC_SUBST([JNILIB], [libxml2j.dylib])
fi
AC_SUBST([FLAG_SHLIB], ["${cflag_of_sharedlib}"])

AC_CONFIG_FILES([Makefile src/main/c/Makefile])
AC_OUTPUT
