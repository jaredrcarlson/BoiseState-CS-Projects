cmake_minimum_required(VERSION 2.8)
project (griffin)
cmake_policy(SET CMP0015 NEW)

macro(get_WIN32_WINNT version)
    if (CMAKE_SYSTEM_VERSION)
        set(ver ${CMAKE_SYSTEM_VERSION})
        string(REPLACE "." "" ver ${ver})
        string(REGEX REPLACE "([0-9])" "0\\1" ver ${ver})
        set(${version} "0x${ver}")
    endif()
endmacro()

if (WIN32)
	get_WIN32_WINNT(ver)
	add_definitions(-D_WIN32_WINNT=${ver})
else()
	set(CMAKE_CXX_FLAGS "-std=gnu++0x  -g")
endif()

set(CMAKE_MODULE_PATH "${CMAKE_SOURCE_DIR}/cmake")
  find_package(Boost 1.55 REQUIRED system thread)
  link_directories("${Boost_LIBRARY_DIRS}")
  
  add_subdirectory(src)

