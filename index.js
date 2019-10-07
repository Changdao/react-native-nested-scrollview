import PropTypes from 'prop-types';
import React from 'react';
import {
	UIManager, findNodeHandle,
	StyleSheet,
	requireNativeComponent,
	View,Image

} from 'react-native';

import IFScrollResponder from './ifresponder';

const createReactClass = require('create-react-class');

const IFScrollView =createReactClass(
	{
		displayName: 'IFScrollView',
		mixins:[IFScrollResponder.Mixin],
		scrollTo: function(
			y,
			x,
			animated,
		  ) {
			if (typeof y === 'number') {
			  console.warn(
				'`scrollTo(y, x, animated)` is deprecated. Use `scrollTo({x: 5, y: 5, ' +
				  'animated: true})` instead.',
			  );
			} else {
			  ({x, y, animated} = y || {});
			}
			this.getScrollResponder().scrollResponderScrollTo({
			  x: x || 0,
			  y: y || 0,
			  animated: animated !== false,
			});
		},
		getInitialState: function() {
			return {
			  ...this.scrollResponderMixinGetInitialState(),
			  layoutHeight: null,
			};
		},

		getScrollResponder: function(){
			return this;
		},

		getNativeComponentName:function(){
			return 'RNIFScrollView'
		},	
		getNativeComponentRef:function(){
			return this.nativeComponentRef
		},
		setNativeProps:function(props) {
			this._scrollViewRef && this._scrollViewRef.setNativeProps(props);
		},
		_setInnerViewRef:function(ref) {
			this._innerViewRef = ref;
		},
		_setScrollViewRef:function(ref){
			this._scrollViewRef = ref;
		},

		getInnerViewNode:function(){
			return ReactNative.findNodeHandle(this._innerViewRef);
		},
		render:function(){
			//console.log('IFSCROLLVIEW','render()');
			const DEPRECATED_sendUpdatedChildFrames = !!this.props.DEPRECATED_sendUpdatedChildFrames;
			const {stickyHeaderIndices} = this.props;
			const hasStickyHeaders = stickyHeaderIndices && stickyHeaderIndices.length > 0;
			const contentContainerStyle = [
				this.props.horizontal && styles.contentContainerHorizontal,
				this.props.contentContainerStyle,
			  ];
			const baseStyle = styles.baseVertical;  
			const props = {
				...this.props,
				//alwaysBounceHorizontal,
				//alwaysBounceVertical,
				style: ([baseStyle, this.props.style]),
				// Override the onContentSizeChange from props, since this event can
				// bubble up from TextInputs
				onContentSizeChange: null,
				//onLayout: this._handleLayout,
				onMomentumScrollBegin: this.scrollResponderHandleMomentumScrollBegin,
				onMomentumScrollEnd: this.scrollResponderHandleMomentumScrollEnd,
				onResponderGrant: this.scrollResponderHandleResponderGrant,
				onResponderReject: this.scrollResponderHandleResponderReject,
				onResponderRelease: this.scrollResponderHandleResponderRelease,
				// $FlowFixMe
				onResponderTerminate: this.scrollResponderHandleTerminate,
				onResponderTerminationRequest: this.scrollResponderHandleTerminationRequest,
				onScroll: this.scrollResponderHandleScroll,
				onScrollBeginDrag: this.scrollResponderHandleScrollBeginDrag,
				onScrollEndDrag: this.scrollResponderHandleScrollEndDrag,
				onScrollShouldSetResponder: this.scrollResponderHandleScrollShouldSetResponder,
				onStartShouldSetResponder: this.scrollResponderHandleStartShouldSetResponder,
				onStartShouldSetResponderCapture: this.scrollResponderHandleStartShouldSetResponderCapture,
				onTouchEnd: this.scrollResponderHandleTouchEnd,
				onTouchMove: this.scrollResponderHandleTouchMove,
				onTouchStart: this.scrollResponderHandleTouchStart,
				onTouchCancel: this.scrollResponderHandleTouchCancel,
				scrollBarThumbImage: Image.resolveAssetSource(this.props.scrollBarThumbImage),
				scrollEventThrottle: hasStickyHeaders
				  ? 1
				  : this.props.scrollEventThrottle,
				sendMomentumEvents:
				  this.props.onMomentumScrollBegin || this.props.onMomentumScrollEnd
					? true
					: false,
				DEPRECATED_sendUpdatedChildFrames,
				// pagingEnabled is overridden by snapToInterval / snapToOffsets
				pagingEnabled: Platform.select({
				  // on iOS, pagingEnabled must be set to false to have snapToInterval / snapToOffsets work
				  ios:
					this.props.pagingEnabled &&
					this.props.snapToInterval == null &&
					this.props.snapToOffsets == null,
				  // on Android, pagingEnabled must be set to true to have snapToInterval / snapToOffsets work
				  android:
					this.props.pagingEnabled ||
					this.props.snapToInterval != null ||
					this.props.snapToOffsets != null,
				}),
			  };
	
	
			
	
			return <RNIFScrollView {...props} ref={this._setScrollViewRef} >
				<View
				// {...contentSizeChangeProps}
				// $FlowFixMe Invalid prop usage
				style={contentContainerStyle}
				ref={this._setInnerViewRef}
				removeClippedSubviews={
				  // Subview clipping causes issues with sticky headers on Android and
				  // would be hard to fix properly in a performant way.
				  Platform.OS === 'android' && hasStickyHeaders
					? false
					: this.props.removeClippedSubviews
				}
				collapsable={false}>
				{this.props.children}
			  </View>
			</RNIFScrollView>;
		}
	}
) 
	

const styles = StyleSheet.create({
	baseVertical: {
	  flexGrow: 1,
	  flexShrink: 1,
	  flexDirection: 'column',
	  overflow: 'scroll',
	},
	baseHorizontal: {
	  flexGrow: 1,
	  flexShrink: 1,
	  flexDirection: 'row',
	  overflow: 'scroll',
	},
	contentContainerHorizontal: {
	  flexDirection: 'row',
	},
  });

/*
KChartView.propTypes = {
	
}
*/

var RNIFScrollView = requireNativeComponent('RNIFScrollView');


export default IFScrollView;


//export default ScrollEnhancer(HighlightEnhancer(ScaleEnhancer(MoveEnhancer(KChartView))))