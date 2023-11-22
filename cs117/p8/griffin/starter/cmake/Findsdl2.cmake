# GNU sdl2
if(SDL2_INCLUDE_DIR AND SDL2_LIBRARY)
  set(SDL2_FOUND TRUE)
else(SDL2_INCLUDE_DIR AND SDL2_LIBRARY)

  #mac with port install
  find_path(
    SDL2_INCLUDE_DIR
    SDL2/SDL.h
    PATHS
    /opt/local/include/
    NO_DEFAULT_PATH)

  find_library(
    SDL2_LIBRARY 
    sdl2
    /opt/local/lib
    NO_DEFAULT_PATH)


  #linux 
  find_path(
    SDL2_INCLUDE_DIR
    SDL2/SDL.h)
  find_library(
    SDL2_LIBRARY
    SDL2)
	
  #windows
  find_path(
    SDL2_INCLUDE_DIR
    SDL2/SDL.h
    PATHS
    "C:/Program Files/SDL"
    NO_DEFAULT_PATH)

  find_library(
    SDL2_LIBRARY 
    sdl2
    "C:/Program Files/SDL/lib/x86"
    NO_DEFAULT_PATH)



  include(FindPackageHandleStandardArgs)
  find_package_handle_standard_args(sdl2 DEFAULT_MSG SDL2_INCLUDE_DIR SDL2_LIBRARY )
  mark_as_advanced(SDL2_INCLUDE_DIR SDL2_LIBRARY)
endif(SDL2_INCLUDE_DIR AND SDL2_LIBRARY)
