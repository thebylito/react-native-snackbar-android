import { NativeModules, DeviceEventEmitter } from 'react-native';
import PropTypes from 'prop-types';

const { ReactNativeSnackBar } = NativeModules;

const duration = {
  LENGTH_LONG: ReactNativeSnackBar.LENGTH_LONG,
  LENGTH_SHORT: ReactNativeSnackBar.LENGTH_SHORT,
  LENGTH_INDEFINITE: ReactNativeSnackBar.LENGTH_INDEFINITE,
};

const SnackBar = {
  show(options) {
    const onPressCallback = (options.action && options.action.onPress) || (() => { });
    const onShowCallback = options.onShow ? options.onShow : null;
    const onHideCallback = options.onHide ? options.onHide : null;

    if (onShowCallback) {
      DeviceEventEmitter.addListener('onShow', () => {
        onShowCallback();
        DeviceEventEmitter.removeCurrentListener();
      });
    }
    if (onHideCallback) {
      DeviceEventEmitter.addListener('onDismiss', () => {
        onHideCallback();
        DeviceEventEmitter.removeCurrentListener();
      });
    }
    if (options.message) options.message = options.message.toString();
    if (options.action.title) options.action.title = options.action.title.toString();
    ReactNativeSnackBar.showSnack(options, onPressCallback);
  },
  dismiss() {
    ReactNativeSnackBar.dismiss();
  },
};

SnackBar.propTypes = {
  message: PropTypes.string.isRequired,
  duration: PropTypes.number,
  backgroundColor: PropTypes.string,
  onShow: PropTypes.func,
  onHide: PropTypes.func,
  action: PropTypes.shape({
    title: PropTypes.string.isRequired,
    onPress: PropTypes.func.isRequired,
    txtColor: PropTypes.string,
  }),
};

export default SnackBar;
export { duration };
