Dir.foreach('src/main/c') { |f|
  IO.write(f, IO.read(f).gsub(/\b(env)->(.+?)\(/, '(*\1)->\2(env, ')) if f=~/\.c$/
}
