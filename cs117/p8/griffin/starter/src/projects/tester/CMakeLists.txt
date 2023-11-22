include_directories("${Boost_INCLUDE_DIR}")
add_executable(tester tester.cpp Finch.cpp Finch.h)

if(WIN32)
 target_link_libraries(tester hidapi )
else()
 target_link_libraries(tester
    hidapi 
    "${Boost_THREAD_LIBRARY}")
endif()



