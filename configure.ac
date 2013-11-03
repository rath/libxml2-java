AC_PREREQ([2.68])
AC_INIT([libxml2-java], [0.1], [http://github.com/rath/libxml2-java])
AC_CONFIG_SRCDIR([src/main/c/libxml.cpp])
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
  | libxml2-java module. Stopping...           |
  | Check 'config.log' for more information.   |
  ---------------------------------------------+])
fi
AC_SUBST([LIBS_XML2], [`xml2-config --libs`])
AC_SUBST([CFLAGS_XML2], [`xml2-config --cflags`])

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
  # TODO: there must be better way to guess system default jdk directories
  if test x"${platform}" = x"darwin"; then
    jdk_dir=$(/usr/libexec/java_home) 
  fi
  if test x"${platform}" = x"linux"; then
    jdk_dir=$(/usr/lib/jvm/java) 
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

AC_CONFIG_FILES([Makefile src/main/c/Makefile])
AC_OUTPUT
