Dir.glob('src/main/c/*.c') { |f|
  IO.write(f, IO.read(f).gsub(/\b(env)->(.+?)\(/, '(*\1)->\2(env, '))
}
