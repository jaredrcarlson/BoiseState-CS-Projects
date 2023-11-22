################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS += \
../testsuite/SimpleTestList.c 

OBJS += \
./testsuite/SimpleTestList.o 

C_DEPS += \
./testsuite/SimpleTestList.d 


# Each subdirectory must supply rules for building sources it contributes
testsuite/%.o: ../testsuite/%.c
	@echo 'Building file: $<'
	@echo 'Invoking: Cross GCC Compiler'
	gcc -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


