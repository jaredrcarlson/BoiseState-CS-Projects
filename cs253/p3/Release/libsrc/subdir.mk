################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS += \
../libsrc/List.c \
../libsrc/Node.c 

O_SRCS += \
../libsrc/List.o \
../libsrc/Node.o 

OBJS += \
./libsrc/List.o \
./libsrc/Node.o 

C_DEPS += \
./libsrc/List.d \
./libsrc/Node.d 


# Each subdirectory must supply rules for building sources it contributes
libsrc/%.o: ../libsrc/%.c
	@echo 'Building file: $<'
	@echo 'Invoking: Cross GCC Compiler'
	gcc -O3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


