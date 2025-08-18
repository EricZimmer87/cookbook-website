import './Home.css';

function HomeView() {
  return (
    <div>
      <h1 className="welcome-header">Welcome to Cooking Made EZ!</h1>
      <div className="welcome-body">
        <p>
          This website will make it easy to share recipes. Create an account to see for yourself!
        </p>
        <p>
          Or you can just browse the recipes...<small>lurker...</small>
        </p>
        <p>
          <small>This app is currently in beta due to time constraints.</small>
        </p>
      </div>
    </div>
  );
}

export default HomeView;
