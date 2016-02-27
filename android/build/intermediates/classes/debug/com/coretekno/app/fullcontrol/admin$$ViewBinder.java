// Generated code from Butter Knife. Do not modify!
package com.coretekno.app.fullcontrol;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class admin$$ViewBinder<T extends com.coretekno.app.fullcontrol.admin> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558568, "field 'pc_name'");
    target.pc_name = finder.castView(view, 2131558568, "field 'pc_name'");
    view = finder.findRequiredView(source, 2131558642, "field 'user_name'");
    target.user_name = finder.castView(view, 2131558642, "field 'user_name'");
    view = finder.findRequiredView(source, 2131558643, "field 'user_connect_id'");
    target.user_connect_id = finder.castView(view, 2131558643, "field 'user_connect_id'");
    view = finder.findRequiredView(source, 2131558540, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131558540, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131558539, "field 'drawer'");
    target.drawer = finder.castView(view, 2131558539, "field 'drawer'");
    view = finder.findRequiredView(source, 2131558541, "field 'navigationView'");
    target.navigationView = finder.castView(view, 2131558541, "field 'navigationView'");
    view = finder.findRequiredView(source, 2131558570, "field 'off_click' and method 'off_click'");
    target.off_click = finder.castView(view, 2131558570, "field 'off_click'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.off_click(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558574, "field 'switch_spy' and method 'switch_spy'");
    target.switch_spy = finder.castView(view, 2131558574, "field 'switch_spy'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.switch_spy(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558576, "field 'switch_mouse' and method 'switch_mouse_lock'");
    target.switch_mouse = finder.castView(view, 2131558576, "field 'switch_mouse'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.switch_mouse_lock(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558577, "field 'horn' and method 'horn_click'");
    target.horn = finder.castView(view, 2131558577, "field 'horn'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.horn_click(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558572, "method 'sleep_click'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.sleep_click(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558579, "method 'lock_click'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.lock_click(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558580, "method 'delete_click'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.delete_click(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558578, "method 'message_click'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.message_click(p0);
        }
      });
  }

  @Override public void unbind(T target) {
    target.pc_name = null;
    target.user_name = null;
    target.user_connect_id = null;
    target.toolbar = null;
    target.drawer = null;
    target.navigationView = null;
    target.off_click = null;
    target.switch_spy = null;
    target.switch_mouse = null;
    target.horn = null;
  }
}
