include recipes-kernel/linux/linux-lmp-fslc-imx.inc

include recipes-kernel/linux/kmeta-linux-lmp-6.1.y.inc

# Use Freescale kernel by default
LINUX_VERSION ?= "6.1.38"
KERNEL_BRANCH ?= "6.1-2.0.x-imx"

SRCREV_machine = "b872b1170fc8843b55e9f8838dd373ff43bb7552"

SRC_URI += " \
    file://0004-FIO-toup-hwrng-optee-support-generic-crypto.patch \
    file://0001-FIO-extras-arm64-dts-imx8mm-evk-use-imx8mm-evkb-for-.patch \
    file://0001-arm64-dts-imx8mq-drop-cpu-idle-states.patch \
    file://0001-FIO-toimx-of-enable-using-OF_DYNAMIC-without-OF_UNIT.patch \
    file://0002-FIO-toup-media-Kconfig-fix-double-VIDEO_DEV.patch \
    file://0003-FIO-toup-gpu-drm-cadence-select-hdmi-helper.patch \
    file://0004-FIO-toup-media-imx8-select-v4l2_-for-mxc-mipi-csi2_y.patch \
"

SRC_URI:append:imx8mp-lpddr4-evk = " \
    ${@bb.utils.contains('MACHINE_FEATURES', 'se05x', 'file://0001-FIO-internal-arch-arm64-dts-imx8mp-enable-I2C5-bus.patch', '', d)} \
"

# Add bluetooth support for QCA9377
SRC_URI:append:imx8mm-lpddr4-evk = " \
    file://0001-FIO-toup-arm64-dts-imx8mm-evk-qca-wifi-enable-suppor.patch \
"
# Fix bluetooth reset for Murata 1MW
SRC_URI:append:mx8mn-nxp-bsp = " \
    file://0001-FIO-internal-arm64-dts-imx8mn-evk.dtsi-re-add-blueto.patch \
"
