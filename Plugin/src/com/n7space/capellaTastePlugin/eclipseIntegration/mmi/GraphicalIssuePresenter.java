// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.eclipseintegration.mmi;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ListDialog;

import com.n7space.capellatasteplugin.capellaintegration.mmi.IssuePresenter;
import com.n7space.capellatasteplugin.capellaintegration.mmi.PresentationFeedback;
import com.n7space.capellatasteplugin.utils.ImageUtils;
import com.n7space.capellatasteplugin.utils.Issue;
import com.n7space.capellatasteplugin.utils.Issue.Kind;

/**
 * Implementation of an issue presenter using GUI primitives supplied by the
 * Eclipse environment.
 */
public class GraphicalIssuePresenter implements IssuePresenter {

	/**
	 * Label provider for issues.
	 */
	protected static class IssueLabelProvider extends LabelProvider {

		protected static final int ICON_SIZE = 16;

		/**
		 * Returns an image which represents the given issue.
		 * 
		 * @param element
		 *            Issue
		 * @return The corresponding image, or null if an error occurs
		 */
		@Override
		public Image getImage(final Object element) {
			final Issue issue = (Issue) element;
			final Display display = Display.getCurrent();
			if (display == null) {
				return null;
			}
			switch (issue.kind) {
			case Error:
				return ImageUtils.getScaledImage(display.getSystemImage(SWT.ICON_ERROR), ICON_SIZE);
			case Info:
				return ImageUtils.getScaledImage(display.getSystemImage(SWT.ICON_INFORMATION), ICON_SIZE);
			case Warning:
				return ImageUtils.getScaledImage(display.getSystemImage(SWT.ICON_WARNING), ICON_SIZE);
			default:
				return ImageUtils.getScaledImage(display.getSystemImage(SWT.ICON_INFORMATION), ICON_SIZE);
			}
		}

		/**
		 * Returns textual issues description.
		 * 
		 * @param element
		 *            Issue
		 * @return Text description
		 */
		@Override
		public String getText(final Object element) {
			final Issue issue = (Issue) element;
			return issue.description;
		}
	}

	protected List<Issue> getIssuesOfType(final List<Issue> issues, final Issue.Kind kind) {
		final List<Issue> result = new LinkedList<Issue>();
		for (final Issue issue : issues) {
			if (issue.kind == kind) {
				result.add(issue);
			}
		}
		return result;
	}

	protected List<Issue> getSortedIssues(final List<Issue> issues) {
		final List<Issue> errors = getIssuesOfType(issues, Kind.Error);
		final List<Issue> warnings = getIssuesOfType(issues, Kind.Warning);
		final List<Issue> infos = getIssuesOfType(issues, Kind.Info);
		final List<Issue> result = new LinkedList<Issue>();
		result.addAll(errors);
		result.addAll(warnings);
		result.addAll(infos);
		return result;
	}

	/**
	 * Implementation of the IssuePresenter interface. Presents the issues to the
	 * user and invokes callback upon interaction completion.
	 * 
	 * @see IssuePresenter
	 * @param issues
	 *            The list of issues
	 * @param callback
	 *            Callback to be invoked upon interaction completion
	 */
	@Override
	public void PresentIssues(final List<Issue> issues, final IssuePresentationCallback callback) {
		final ListDialog dialog = new ListDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
		dialog.setTitle("Issues");
		dialog.setMessage("The following issues were detected while converting the Capella model:");
		dialog.setLabelProvider(new IssueLabelProvider());
		dialog.setInput(getSortedIssues(issues));
		dialog.setContentProvider(new IStructuredContentProvider() {

			@Override
			public Object[] getElements(final Object inputElement) {
				@SuppressWarnings("unchecked")
				final List<Issue> inputIssues = (List<Issue>) inputElement;
				final Object[] issueArray = new Object[inputIssues.size()];
				int index = 0;
				for (final Issue issue : inputIssues)
					issueArray[index++] = issue;
				return issueArray;
			}
		});
		if (dialog.open() == Window.OK)
			callback.onIssuePresentationFeedback(issues, PresentationFeedback.Accepted);
		else
			callback.onIssuePresentationFeedback(issues, PresentationFeedback.Rejected);
	}

}
