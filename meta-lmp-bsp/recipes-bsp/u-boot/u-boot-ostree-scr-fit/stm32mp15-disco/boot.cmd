echo "Using ${fdtfile}"

# Default boot type and device
setenv bootlimit 3
setenv devtype ${boot_device}
setenv devnum ${boot_instance}
setenv rootpart 6

setenv loadaddr ${ramdisk_addr_r}

setenv bootcmd_resetvars 'setenv kernel_image; setenv bootargs; setenv kernel_image2; setenv bootargs2'
setenv bootcmd_otenv 'run bootcmd_resetvars; ext4load ${devtype} ${devnum}:${rootpart} ${loadaddr} /boot/loader/uEnv.txt; env import -t ${loadaddr} ${filesize} kernel_image bootargs kernel_image2 bootargs2'
setenv bootcmd_load_f 'ext4load ${devtype} ${devnum}:${rootpart} ${loadaddr} "/boot"${kernel_image}'
setenv bootcmd_run 'bootm ${loadaddr}#conf-${fdtfile}'
setenv bootcmd_rollbackenv 'setenv kernel_image ${kernel_image2}; setenv bootargs ${bootargs2}'
setenv bootcmd_set_rollback 'if test ! "${rollback}" = "1"; then setenv rollback 1; setenv upgrade_available 0; saveenv; fi'
setenv bootostree 'run bootcmd_load_f; run bootcmd_run'
setenv altbootcmd 'run bootcmd_otenv; run bootcmd_set_rollback; if test -n "${kernel_image2}"; then run bootcmd_rollbackenv; fi; run bootostree; reset'

env info -d -q; if test $? -eq 0; then saveenv; fi

if test "${rollback}" = "1"; then run altbootcmd; else run bootcmd_otenv; run bootostree; if test ! "${upgrade_available}" = "1"; then setenv upgrade_available 1; saveenv; fi; reset; fi

reset
