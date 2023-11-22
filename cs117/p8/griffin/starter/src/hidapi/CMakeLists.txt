#USB shim
include_directories("${Boost_INCLUDE_DIR}")
add_library(hidapi SHARED hid.cpp hidapi.h ../common/common.hpp)

if (NOT WIN32)
 target_link_libraries(hidapi
    "${Boost_SYSTEM_LIBRARY}"
    "${Boost_LIBRARY}")
endif()
	


