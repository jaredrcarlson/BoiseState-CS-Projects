################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS += \
../testsuite/Object.c \
../testsuite/Test.c \
../testsuite/UnitTestList.c 

OBJS += \
./testsuite/Object.o \
./testsuite/Test.o \
./testsuite/UnitTestList.o 

C_DEPS += \
./testsuite/Object.d \
./testsuite/Test.d \
./testsuite/UnitTestList.d 


# Each subdirectory must supply rules for building sources it contributes
testsuite/%.o: ../testsuite/%.c
	@echo 'Building file: $<'
	@echo 'Invoking: Cross GCC Compiler'
	gcc -O3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


