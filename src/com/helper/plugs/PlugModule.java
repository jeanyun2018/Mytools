package com.helper.plugs;
/**
 * 插件抽象类，可实现该类来实现插件。
 * 初始化消耗时间的操作请放在moduleInit函数中，初始化的时候自动以多线程的方式执行
 * @author jx
 *
 */
public abstract class PlugModule implements IPlugModule
{
		private ParameterBridge parameterBridge;
		
		public void setParameterBridge(ParameterBridge parameterBridge) {
			this.parameterBridge = parameterBridge;
		}
		public ParameterBridge getParameterBridge() {
			return parameterBridge;
		}
		public abstract void moduleInit(); 
		
}
