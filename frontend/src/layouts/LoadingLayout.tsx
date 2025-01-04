const LoadingLayout = () => {
    return (
        <div className="flex items-center justify-center min-h-[var(--full-screen)] gap-2 flex-wrap">
            <div className="animate-spin rounded-full size-10 border-t-2 border-b-2 border-primary"></div>
            <h2>Loading</h2>
        </div>
    );
};

export default LoadingLayout;
